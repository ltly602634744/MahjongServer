//package com.mahjong.server.controller;
//
//import com.mahjong.server.entities.card.Card;
//import com.mahjong.server.entities.handCard.HandCard;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import javax.annotation.PostConstruct;
//import java.util.Set;
//import java.util.TreeSet;
//
//@Controller
//@RequestMapping("/tingpai")
//public class CheckTingPaiControllerOld {
//
//    private HandCard handCard;
//
//    @PostConstruct
//    private void initial() {
//        this.handCard = new HandCard(new TreeSet<>());
//    }
//
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
//
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
//
//
//
//
//}
