package com.hostpet.hostpet.services;

import com.hostpet.hostpet.dtos.BaiaStatusDTO;
import com.hostpet.hostpet.entity.Baia;
import com.hostpet.hostpet.entity.User;
import com.hostpet.hostpet.exceptions.CustomException;
import com.hostpet.hostpet.forms.BaiaForm;
import com.hostpet.hostpet.repository.BaiaRepository;
import com.hostpet.hostpet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaiaService {

    @Autowired
    private BaiaRepository baiaRepository;

    @Autowired
    private UserRepository userRepository;

    // Método para salvar a baia
    public Baia saveBaia(BaiaForm form ) {
        User verifyUser = userRepository.findById(form.userId).orElseThrow(() ->
                new CustomException("Usuario nao encontrado", HttpStatus.BAD_REQUEST)
        );
        var baia = new Baia();
        baia.setUser(verifyUser);
        baia.setDescricao(form.descricao);

        return baiaRepository.save(baia);
    }

    public BaiaStatusDTO calcularTotaisBaia(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        long livres = baiaRepository.countByUserIdAndStatus(userId, "Livre");
        long ocupadas = baiaRepository.countByUserIdAndStatus(userId, "Ocupada");

        return new BaiaStatusDTO(livres, ocupadas);
    }

    // Método para listar todas as baias de um user
    public List<Baia> getAllBaiasByUser(Long userId) {

        return baiaRepository.findByUserId(userId);
    }

    // Método para excluir a baia
    public void deleteBaia(Integer id) {
        baiaRepository.deleteById(id);
    }
}
