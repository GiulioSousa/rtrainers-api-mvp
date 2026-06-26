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

        List<Agenda> agendas = agendaRepository.buscarTodos().stream()
                .filter(agenda -> {
                    LocalTime horario = agenda.getHorario();
                    return horario != null
                            && !horario.isBefore(limiteInicio)
                            && !horario.isAfter(agora);
                })
                .toList();

        return agendas.stream().map(agenda -> {
            AlunoDTO alunoDTO = null;
            try {
                Aluno aluno = alunoRepository.buscarPorId(agenda.getAlunoId());
                if (aluno != null) {
                    alunoDTO = new AlunoDTO(
                            aluno.getId(),
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
                    agenda.getId(),
                    agenda.getAlunoId(),
                    agenda.getProfessorId(),
                    agenda.getTurno(),
                    agenda.getHorario(),
                    agenda.getDiaSemana(),
                    alunoDTO);
        }).toList();
    }

    public void registrarAvaliacao(AvaliacaoDTO dto) throws IOException {
        LocalDate hoje = LocalDate.now();

        if (avaliacaoRepository.existeAvaliacaoNoDia(dto.getAlunoId(), hoje)) {
            throw new RuntimeException("Avaliação já registrada para este aluno hoje");
        }

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setSessaoId(dto.getSessaoId());
        avaliacao.setAlunoId(dto.getAlunoId());
        avaliacao.setProfessorId(dto.getProfessorId());
        avaliacao.setNotaAula(dto.getNotaAula());
        avaliacao.setNotaProfessor(dto.getNotaProfessor());
        avaliacao.setTags(dto.getTags());
        avaliacao.setData(hoje);

        avaliacaoRepository.salvar(avaliacao);
    }
}