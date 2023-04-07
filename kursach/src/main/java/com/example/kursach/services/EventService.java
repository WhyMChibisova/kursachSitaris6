package com.example.kursach.services;

import com.example.kursach.models.Event;
import com.example.kursach.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public List<Event> findById(int id) {
        Optional<Event> event = eventRepository.findById(id);
        ArrayList<Event> result = new ArrayList<>();
        event.ifPresent(result::add);
        return result;
    }

    public void editEvent(int id, String name, LocalDate date, LocalTime time, String place, String language, String description) {
        Event event = eventRepository.findById(id).orElseThrow();
        event.setName(name);
        event.setDate(date);
        event.setTime(time);
        event.setPlace(place);
        event.setLanguage(language);
        event.setDescription(description);
        eventRepository.save(event);
    }

    public void deleteEvent(int id) {
        Event event = eventRepository.findById(id).orElseThrow();
        eventRepository.delete(event);
    }

    public boolean existsById(int id) {
        return eventRepository.existsById(id);
    }

    public void addEvent(Event event) {
        eventRepository.save(event);
    }
}
