package com.illogicalparadox.illogicalParadox.scheduler;

import com.illogicalparadox.illogicalParadox.cache.AppCache;
import com.illogicalparadox.illogicalParadox.entity.JournalEntry;
import com.illogicalparadox.illogicalParadox.entity.User;
import com.illogicalparadox.illogicalParadox.enums.Sentiment;
import com.illogicalparadox.illogicalParadox.repository.UserRepositoryImpl;
import com.illogicalparadox.illogicalParadox.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserScheduler {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private AppCache appCache;
//
//    @Autowired
//    private SentimentAnalysisService sentimentAnalysisService;

    //@Scheduled(cron = "*/5 * * * *")
//    @Scheduled(cron = "0 */10 * ? * *")
//    public void fetchUserAndSendSaMail(){
//        List<User> users= userRepository.getUserForSA();
//        for (User user: users){
//            List<JournalEntry> journalEntries= user.getJournalEntries();
//            List<String> filteredEntries= journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getContent()).collect(Collectors.toList());
//            String entry=String.join(" ",filteredEntries);
//            String sentiment=sentimentAnalysisService.getSentiment(entry);
//            emailService.sendEmail(user.getEmail(), "Sentiment analyses for last 7 days",sentiment);
//        }
//    }
//
//    @Scheduled(cron = "0 */10 * ? * *")
//    public void clearAppCache(){
//        appCache.init();
//    }

    public void fetchUserAndSendsSaMail(){
        List<User> users=userRepository.getUserForSA();
        for(User user:users){
            List<JournalEntry> journalEntries=user.getJournalEntries();
            List<Object> sentiments= journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> sentimentCounts= new HashMap<>();
            for(Object sentiment:sentiments){
                if(sentiment!=null){
                    sentimentCounts.put((Sentiment) sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);

                }
                Sentiment mostFrequentlSentiment=null;
                int maxCount=0;
                for(Map.Entry<Sentiment,Integer> entry: sentimentCounts.entrySet()){
                    if (entry.getValue()>maxCount){
                        maxCount=entry.getValue();
                        mostFrequentlSentiment=entry.getKey();

                    }
                }
                if (mostFrequentlSentiment!= null){
                    emailService.sendEmail(user.getEmail(),"Sentiment for last 9 days",mostFrequentlSentiment.toString());
                }
            }
        }
    }


}
