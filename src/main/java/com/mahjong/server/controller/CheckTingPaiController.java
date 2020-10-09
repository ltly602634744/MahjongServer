package com.mahjong.server.controller;

import com.mahjong.server.ErrorResponse.ErrorResponse;
import com.mahjong.server.entities.card.Card;
import com.mahjong.server.entities.card.TiaoCard;
import com.mahjong.server.entities.handCard.HandCard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@RestController
@RequestMapping("/tingpai")
public class CheckTingPaiController {

//    private HandCard handCard;
//
//    @PostConstruct
//    private void initial() {
//        this.handCard = new HandCard(new TreeSet<>());
//    }

//    public void addCard(int value, String type){
//        int typeNum = -1;
//        if (type.equals("万")){
//            typeNum = 0;
//        }else if(type.equals("桶")){
//            typeNum = 1;
//        }else if(type.equals("条")){
//            typeNum = 2;
//        }
//        this.handCard.moPai(new Card(value, typeNum));
//    }

//    @GetMapping
//    public String checkTingPage(Model theModel) {
//
////        List<Employee> theEmployees = employeeService.findAll();
////
////        //add to the spring model
//        theModel.addAttribute("cards", this.handCard);
//
//        return "tingCalculate";
//    }
//
////    @GetMapping("/result")
//    public Set<Card> getTingPaiResult(Model theModel){
//        return this.handCard.tingPaiList();
//    }

    @GetMapping("/result")
    public Set<Card> getTingPaiResult(){
        HandCard handCard = new HandCard(new TreeSet<Card>());
        handCard.moPai(new TiaoCard(1));
        handCard.moPai(new TiaoCard(2));
        System.out.println(handCard);
        return handCard.getHandCards();
    }


    @PostMapping("/calculate")
    public Set<Card> tingPaiList(@RequestBody List<Card> cards){
        HandCard handCard = new HandCard();
        handCard.setHandCards(new TreeSet<Card>(cards));
        Set<Card> result = handCard.tingPaiList();

        return handCard.tingPaiList();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e){
        ErrorResponse error = new ErrorResponse();

        error.setStatus(HttpStatus.OK.value());
        error.setMessage(e.getMessage());
        error.setTimeStamp(Instant.now().toEpochMilli());

        return new ResponseEntity<>(error, HttpStatus.OK);
    }




}
