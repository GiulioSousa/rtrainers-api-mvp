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

    public List<AgendaDTO> buscarAgendaPorProfessor(Integer professorId) throws IOException {
        List<Agenda> agendas = agendaRepository.buscarPorProfessor(professorId);

        return agendas.stream().map(agenda -> {
            AlunoDTO alunoDTO = null;
            try {
                Aluno aluno = alunoRepository.buscarPorId(agenda.getAlunoId());
                if (aluno != null) {
                    alunoDTO = new AlunoDTO(
                            aluno.getId(),
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
                    agenda.getId(),
                    agenda.getAlunoId(),
                    agenda.getProfessorId(),
                    agenda.getTurno(),
                    agenda.getHorario(),
                    agenda.getDiaSemana(),
                    alunoDTO);
        }).toList();
    }

    public void registrarPsr(String intervalo, Integer psr) throws IOException {
        sessaoRepository.registrarPsr(intervalo, psr);
    }

    public void registrarPse(String intervalo, Integer pse) throws IOException {
        sessaoRepository.registrarPse(intervalo, pse);
    }
}