package com.jkdev.bookmyshow.service;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    private record Session(Long userId, Instant createdAt, Instant expiresAt) {}

    private final Map<String, Session> sessions = new ConcurrentHashMap<>();
    private final long ttlSeconds = 3600; // 1 hour

    // Create a new session
    public String createSession(Long userId) {
        String token = UUID.randomUUID().toString();
        Instant now = Instant.now();
        sessions.put(token, new Session(userId, now, now.plusSeconds(ttlSeconds)));
        return token;
    }

    // Validate a session token
    public boolean isValid(String token) {
        Session session = sessions.get(token);
        if (session == null) {
            return false; // Session does not exist
        }
        if (session.expiresAt().isBefore(Instant.now())) {
            sessions.remove(token); // Remove expired session
            return false;
        }
        return true;
    }

    public void invalidate(String token) {
        sessions.remove(token);
    }
}