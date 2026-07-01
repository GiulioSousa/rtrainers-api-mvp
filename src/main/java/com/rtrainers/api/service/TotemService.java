package com.rtrainers.api.service;

import com.rtrainers.api.dto.AgendaDTO;
import com.rtrainers.api.dto.AlunoDTO;
import com.rtrainers.api.dto.AvaliacaoDTO;
import com.rtrainers.api.model.Agenda;
import com.rtrainers.api.model.Aluno;
import com.rtrainers.api.model.Avaliacao;
import com.rtrainers.api.repository.AgendaRepository;
import com.rtrainers.api.repository.AlunoRepository;
import com.rtrainers.api.repository.AvaliacaoRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TotemService {

    private final AgendaRepository agendaRepository;
    private final AlunoRepository alunoRepository;
    private final AvaliacaoRepository avaliacaoRepository;

    public TotemService(AgendaRepository agendaRepository,
            AlunoRepository alunoRepository,
            AvaliacaoRepository avaliacaoRepository) {
        this.agendaRepository = agendaRepository;
        this.alunoRepository = alunoRepository;
        this.avaliacaoRepository = avaliacaoRepository;
    }

    public List<AgendaDTO> buscarAlunosNoHorarioAtual() throws IOException {
        LocalTime agora = LocalTime.now();
        LocalTime limiteInicio = agora.minusHours(1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("H:mm");

        List<Agenda> agendas = agendaRepository.buscarTodos().stream()
                .filter(agenda -> {
                    try {
                        LocalTime horario = LocalTime.parse(agenda.getHorario(), formatador);
                        return !horario.isBefore(limiteInicio) && !horario.isAfter(agora);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .toList();

        return agendas.stream().map(agenda -> {
            AlunoDTO alunoDTO = null;
            try {
                Aluno aluno = alunoRepository.buscarPorNome(agenda.getNomeAluno());
                if (aluno != null) {
                    alunoDTO = new AlunoDTO(
                            null,
                            aluno.getNome(),
                            null,
                            null,
                            null,
                            null,
                            null);
                }
            } catch (IOException e) {
                throw new RuntimeException("Erro ao buscar aluno", e);
            }

            return new AgendaDTO(
                    agenda.getNomeAluno(),
                    agenda.getNomeProfessor(),
                    agenda.getTurno(),
                    agenda.getHorario(),
                    agenda.getDiaSemana(),
                    agenda.getEstimuloTreino(),
                    alunoDTO);
        }).toList();
    }

    public void registrarAvaliacao(AvaliacaoDTO dto) throws IOException {
        LocalDate hoje = LocalDate.now();

        if (avaliacaoRepository.existeAvaliacaoNoDia(dto.getNomeAluno(), hoje)) {
            throw new RuntimeException("Avaliação já registrada para este aluno hoje");
        }

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setNomeAluno(dto.getNomeAluno());
        avaliacao.setNomeProfessor(dto.getNomeProfessor());
        avaliacao.setNotaAula(dto.getNotaAula());
        avaliacao.setNotaProfessor(dto.getNotaProfessor());
        avaliacao.setTags(dto.getTags());
        avaliacao.setData(hoje);

        avaliacaoRepository.salvar(avaliacao);
    }
}