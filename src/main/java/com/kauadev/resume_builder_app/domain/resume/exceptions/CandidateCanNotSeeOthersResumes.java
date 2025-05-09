package com.kauadev.resume_builder_app.domain.resume.exceptions;

public class CandidateCanNotSeeOthersResumes extends RuntimeException {

    public CandidateCanNotSeeOthersResumes() {
        super("Usuário candidato não pode ver currículo de outros usuários");
    }

    public CandidateCanNotSeeOthersResumes(String msg) {
        super(msg);
    }

}
