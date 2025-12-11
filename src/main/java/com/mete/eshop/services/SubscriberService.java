package com.mete.eshop.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mete.eshop.model.Subscriber;
import com.mete.eshop.model.SubscriberRepository;

@Service
public class SubscriberService {

    private final SubscriberRepository repository;

    SubscriberService(SubscriberRepository rep) {
        super();
        this.repository = rep;
    }

    public List<Subscriber> getAll(){
        var l = new ArrayList<Subscriber>();
        for(Subscriber r : repository.findAll())
        {
            l.add(r);
        }
        return l;
    }

    public Subscriber get(Integer id){
        return repository.findById(id).get();
    }

    public boolean isSubscriber(String email){
        return repository.findByEmail(email) != null;
    }

    public void addSubscriber(String email){
        if(isSubscriber(email)) return;
        Subscriber sub = new Subscriber();
        sub.setEmail(email);
        repository.save(sub);
    }

   
}

