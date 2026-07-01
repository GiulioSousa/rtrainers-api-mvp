package com.rtrainers.api.service;

import com.rtrainers.api.dto.AgendaDTO;
import com.rtrainers.api.dto.AlunoDTO;
import com.rtrainers.api.model.Agenda;
import com.rtrainers.api.model.Aluno;
import com.rtrainers.api.repository.AgendaRepository;
import com.rtrainers.api.repository.AlunoRepository;
import com.rtrainers.api.repository.SessaoRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProfessorService {

    private final AgendaRepository agendaRepository;
    private final AlunoRepository alunoRepository;
    private final SessaoRepository sessaoRepository;

    public ProfessorService(AgendaRepository agendaRepository,
            AlunoRepository alunoRepository,
            SessaoRepository sessaoRepository) {
        this.agendaRepository = agendaRepository;
        this.alunoRepository = alunoRepository;
        this.sessaoRepository = sessaoRepository;
    }

    public List<AgendaDTO> buscarAgendaPorProfessor(String nomeProfessor) throws IOException {
        List<Agenda> agendas = agendaRepository.buscarPorProfessor(nomeProfessor);

        return agendas.stream().map(agenda -> {
            AlunoDTO alunoDTO = null;
            try {
                Aluno aluno = alunoRepository.buscarPorNome(agenda.getNomeAluno());
                if (aluno != null) {
                    alunoDTO = new AlunoDTO(
                            null,
                            aluno.getNome(),
                            aluno.getEstagio(),
                            aluno.getLesao(),
                            aluno.getPreferencia(),
                            aluno.getObjetivo(),
                            aluno.getObservacoes());
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

    public void registrarPsr(String nomeProfessor, String horario, String nomeAluno, LocalDate data, Integer psr) throws IOException {
    sessaoRepository.registrarPsr(nomeProfessor, horario, nomeAluno, data, psr);
}

public void registrarPse(String nomeProfessor, String horario, String nomeAluno, LocalDate data, Integer pse) throws IOException {
    sessaoRepository.registrarPse(nomeProfessor, horario, nomeAluno, data, pse);
}
}