package com.rjvince.words;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RestController
public class WordsController {
    private static final LocalDate epoch = LocalDate.of(2019, 7, 24);
    private static List<String> wordList = null;

    public WordsController() throws IOException {
        InputStream wordListStream = new ClassPathResource("data/word_list.txt").getInputStream();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(wordListStream))) {
            wordList = reader.lines().collect(Collectors.toList());
            Random r = new Random(epoch.toEpochDay());
            Collections.shuffle(wordList, r);
        } catch (IOException e) {
            wordList.add("*** Failed to read word list");
        }
    }

    @RequestMapping("/word/today")
    public String wordToday() {
        int day = Period.between(epoch, LocalDate.now()).getDays();
        return wordList.get(day);
    }

    @RequestMapping("/word/day/{day}")
    public String wordOnDay(@PathVariable Integer day) {
        return wordList.get(day % wordList.size());
    }

    @RequestMapping("/word/random")
    public String wordRandom(@RequestParam(name = "many", defaultValue = "1") int many) {
        List<String> tempList = new ArrayList<>(wordList.size());
        tempList.addAll(wordList);

        Random r = new Random();
        Collections.shuffle(tempList, r);

        List<String> resultList = tempList.subList(0, many >= wordList.size() ? wordList.size() : many);

        return String.join(", ", resultList);
    }
}
