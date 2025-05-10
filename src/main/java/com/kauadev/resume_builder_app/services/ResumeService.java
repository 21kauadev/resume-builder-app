package com.kauadev.resume_builder_app.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kauadev.resume_builder_app.domain.resume.Resume;
import com.kauadev.resume_builder_app.domain.resume.exceptions.AdminCanNotHaveResumesException;
import com.kauadev.resume_builder_app.domain.resume.exceptions.CandidateCanNotSeeOthersResumes;
import com.kauadev.resume_builder_app.domain.resume.exceptions.ResumeNotFoundException;
import com.kauadev.resume_builder_app.domain.user.User;
import com.kauadev.resume_builder_app.repositories.ResumeRepository;

@Service
public class ResumeService {

    @Autowired
    private ResumeRepository resumeRepository;

    private User getLoggedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User loggedUser = (User) auth.getPrincipal();

        return loggedUser;
    }

    public List<Resume> getAllResumes() {
        if (!getLoggedUser().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new CandidateCanNotSeeOthersResumes();
        }

        List<Resume> resumes = resumeRepository.findAll();

        return resumes;
    }

    public Resume getResume(Long id) {
        Resume resume = resumeRepository.findById(id).orElseThrow(ResumeNotFoundException::new);

        return resume;
    }

    // só usuario candidato pode ver seu proprio curriculo, pois
    // admins não postam curriculos. eles verão os curriculos postados pelos
    // candidatos
    public Resume getUserResume() {
        User loggedUser = this.getLoggedUser();

        if (loggedUser.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AdminCanNotHaveResumesException();
        }

        // checando todos os resumes
        List<Resume> resumes = resumeRepository.findAll();

        // filtra, compara se o id do usuario que enfviou o resume é o mesmo do logado
        // se sim, por ser apenas um, retorna o índice 0, o primeiro índice.
        // retornando apenas o objeto
        Resume userResume = resumes
                .stream()
                .filter((resume) -> resume.getUser().getId() == loggedUser.getId()).toList()
                .get(0);

        return userResume;
    }

    public Resume uploadResume(MultipartFile file, String position) throws IOException {

        // SALVANDO LOCALMENTE

        // gerando um nome aleatorio com base em um UUID e o nome orignial do arquivo
        String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();

        // se a pasta não existir, cria ela.
        if (!Files.exists(Paths.get("uploads/"))) {
            Files.createDirectory(Paths.get("uploads/"));
        }

        // objeto path, representa o caminho no disco onde será salvo
        Path path = Paths.get("uploads/" + filename);

        // escreve o conteúdo binário do arquivo enviado no caminho especificado
        Files.write(path, file.getBytes());
        // até aqui já foi SALVO LOCALMENTE.

        Resume resume = new Resume(path.toString(), position, getLoggedUser());

        // SALVANDO NO BANCO DE DADOS
        return resumeRepository.save(resume);
    }

    public void deleteResume(Long id) throws IOException {
        Resume resume = resumeRepository.findById(id).orElseThrow(ResumeNotFoundException::new);

        // pega o caminho e deleta se houver o arquivo lá também. assim, deleta o
        // caminho não só da base de dados, como da pasta uploads também
        Path path = Paths.get(resume.getFilePath());
        Files.deleteIfExists(path);

        resumeRepository.delete(resume);
    }

}
