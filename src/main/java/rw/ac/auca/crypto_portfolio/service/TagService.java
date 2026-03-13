package rw.ac.auca.crypto_portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rw.ac.auca.crypto_portfolio.entity.Tag;
import rw.ac.auca.crypto_portfolio.repository.TagRepository;
import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public Tag save(String name) {
        if (tagRepository.existsByName(name)) {
            throw new RuntimeException(
                    "Tag " + name + " already exists");
        }
        Tag tag = new Tag();
        tag.setName(name);
        return tagRepository.save(tag);
    }

    public List<Tag> findAll() {
        return tagRepository.findAll();
    }

    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }
}