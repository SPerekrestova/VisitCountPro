package com.sperekrestova.visitCount.user;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Svetlana
 * Date: 04.01.2021
 */
@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {

        confirmationTokenRepository.save(confirmationToken);
    }

    public void deleteConfirmationToken(Long id) {

        confirmationTokenRepository.deleteById(id);
    }

    public Optional<ConfirmationToken> findConfirmationTokenByToken(String token) {

        return confirmationTokenRepository.findConfirmationTokenByConfirmationToken(token);
    }
}