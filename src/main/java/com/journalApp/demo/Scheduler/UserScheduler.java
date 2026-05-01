package com.journalApp.demo.Scheduler;

import com.journalApp.demo.entity.JournalEntry;
import com.journalApp.demo.entity.User;
import com.journalApp.demo.enums.Sentiment;
import com.journalApp.demo.model.SentimentData;
import com.journalApp.demo.repository.UserRepositoryImpl;
import com.journalApp.demo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserScheduler {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private KafkaTemplate<String, SentimentData> kafkaTemplate;


    @Scheduled(cron="0 0  9 * * SUN ")
    public void fetchUsersAndSendSaEmail(){
        List<User> users =userRepository.getUsersForSA();
        for(User user:users){
            List<JournalEntry> journalEntries =user.getJournalEntries();
            List<Sentiment> Sentiments= journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment,Integer> sentimentCounts=new HashMap<>();
            for(Sentiment sentiment:Sentiments){
                if(sentiment!=null){
                    sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
                }
            }
            Sentiment mostFrequentSentiment=null;
            int maxCount=0;
            for(Map.Entry<Sentiment,Integer> entry:sentimentCounts.entrySet()){
                if(entry.getValue()>maxCount){
                    maxCount=entry.getValue();
                    mostFrequentSentiment=entry.getKey();
                }
            }
            if(mostFrequentSentiment!=null){
                SentimentData sentimentData = SentimentData.builder().email(user.getEmail()).sentiment("Sentiment for last 7 days " + mostFrequentSentiment).build();
                try{
                    kafkaTemplate.send("weekly-sentiments", sentimentData.getEmail(), sentimentData);
                }catch (Exception e){
                    emailService.sendEmail(sentimentData.getEmail(), "Sentiment for previous week", sentimentData.getSentiment());
                }
               // emailService.sendEmail(user.getEmail(),"sentiment for last 7 days",mostFrequentSentiment.toString());

            }
        }
    }
}

/**
 *
 * fetch users-criteria based user fetch from UserRepsoitoryImpl
 * send email-java mail sender
 */
