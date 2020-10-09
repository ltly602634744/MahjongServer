package com.mahjong.server.tools;


import com.mahjong.server.entities.card.Card;
import com.mahjong.server.entities.card.WanCard;
import com.mahjong.server.entities.handCard.HandCard;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

public class TingPai {

    public static Map<Integer, List<Card>> menPaiMap(Set<Card> cards){
//        if (cards.size() == 0){
//            return new HashMap<Integer, List<Card>>();
//        }
//        System.out.println(cards.stream()
//                .collect(Collectors.groupingBy(x -> x.getCardType())));
        return cards.stream()
                .collect(Collectors.groupingBy(x -> x.getCardType()));
    }

    public static void distinct(Set<Card> cards){
        Iterator<Card> cardIterator = cards.iterator();
        Card theCard = null;
        if (cardIterator.hasNext()){
            theCard = cardIterator.next();
        }
        while(cardIterator.hasNext()){
            Card tempCard = cardIterator.next();
            if(theCard.sameCard(tempCard)){
                cardIterator.remove();
            }else{
                theCard = tempCard;
            }
        }
    }

    public static boolean sanPaiChengKe(Card card1, Card card2, Card card3){
        return card1.chengKe(card2, card3);
    }

    public static boolean sanPaiChengShun(Card card1, Card card2, Card card3){
        return card1.chengShun(card2, card3);
    }

    //返回听七对的牌
    public static Card checkTingQiDuiCard(List<Card> cards){
        if (cards.size() == 13){
            Card result = null;
            int i = 1;
            boolean flag = true;
            for(i = 1; i < 13; i= i + 2){
                if(!cards.get(i).sameCard(cards.get(i-1))){
                    if(flag){
                        i--;
                        result = cards.get(i);
                        flag = false;
                    }else{
                        break;
                    }
                }
            }
            if(i == 13 && flag){
                result = cards.get(12);
            }
            if(i > 12){
                return result;
            }

        }

        return null;
    }

    public static Set<Card> calculateTingPaiList(Set<Card> cards){
        Set<Card> result = new TreeSet<>();
        int cardNum = cards.size();

        if (cardNum == 1){
            result.addAll(cards);
            return result;
        }

        Card qiDui = checkTingQiDuiCard(new ArrayList<>(cards));
        if(qiDui != null){
            result.add(qiDui);
        }

        if(cardNum > 3){
            for(int i = 0; i < cardNum-2; i ++){
                for(int j = i + 1 ; j < cardNum-1; j ++){
                    for(int k = j + 1; k < cardNum; k++){
                        List<Card> cardList = new ArrayList<>(cards);
                        Card card1 = cardList.get(i);
                        Card card2 = cardList.get(j);
                        Card card3 = cardList.get(k);
                        if(sanPaiChengKe(card1, card2, card3) || sanPaiChengShun(card1, card2, card3)){
                            cardList.remove(card1);
                            cardList.remove(card2);
                            cardList.remove(card3);
                            result.addAll(calculateTingPaiList(new TreeSet<>(cardList)));
                        }
                    }
                }
            }
        }

        if ((cardNum-1) % 3 == 0){
            for(int i = 0; i < cardNum-1; i ++) {
                for (int j = i + 1; j < cardNum; j++) {
                    List<Card> cardList = new ArrayList<>(cards);
                    Card card1 = cardList.get(i);
                    Card card2 = cardList.get(j);
                    if(card1.sameCard(card2)){
                        cardList.remove(card1);
                        cardList.remove(card2);

                        Set<Card> cardList2 = new TreeSet<>(cardList);

                        result.addAll(calculateTingPaiList(new TreeSet<>(cardList2)));
                    }
                }
            }
        }

        if (cardNum == 2){
            List<Card> cardList = new ArrayList<>(cards);
//            System.out.println("cardList");
            Set<Card> tingList = cardList.get(0).twoCardsTing(cardList.get(1));
            if (tingList != null){
                result.addAll(tingList);
            }
        }


        distinct(result);
        return result;
    }

    //返回所有情况的听牌列表
    public static Set<Card> checkTingPaiList(Set<Card> cards){
        int cardNum = cards.size();
        if (cardNum % 3 != 1){
            throw new RuntimeException("牌的数量不合法！");
        }
        return calculateTingPaiList(cards);
    }

    //返回一张牌时的听牌列表
    public static Set<Card> check1Card(Set<Card> cards){
        Set<Card> result = new TreeSet<>();
        if (cards.size() == 1){
            result.addAll(cards);
            return result;
        }
        throw new RuntimeException("牌的数量不为一");
    }

    //返回4张手牌时候的听牌列表
    public static Set<Card> check4Card(Set<Card> cards){

        Set<Card> result = new TreeSet<>();
        /*TODO
         *   1.找出三张可以成刻子或者顺子，则听单张 将牌
         *   2.找出两张相同的牌做将， 检查剩余两张是否可与某张牌组成顺子或刻字， 如果可以，听这张缺的牌
         */
        Map<Integer, List<Card>> menPaiMap = menPaiMap(cards);
        if(menPaiMap.keySet().size()==3){
            return result;
        }


        for(int i = 0; i < 2; i ++){
            for(int j = i + 1 ; j < 3; j ++){
                for(int k = j + 1; k < 4; k++){
                    List<Card> cardList = new ArrayList<>(cards);
                    Card card1 = cardList.get(i);
                    Card card2 = cardList.get(j);
                    Card card3 = cardList.get(k);
                    if(sanPaiChengKe(card1, card2, card3) || sanPaiChengShun(card1, card2, card3)){
                        cardList.remove(card1);
                        cardList.remove(card2);
                        cardList.remove(card3);
                        result.addAll(cardList);
                    }
                }
            }
        }

        for(int i = 0; i < 3; i ++) {
            for (int j = i + 1; j < 4; j++) {
                List<Card> cardList = new ArrayList<>(cards);
                Card card1 = cardList.get(i);
                Card card2 = cardList.get(j);
                if(card1.sameCard(card2)){
                    cardList.remove(card1);
                    cardList.remove(card2);
                    Set<Card> tingList = cardList.get(0).twoCardsTing(cardList.get(1));
                    if (tingList != null){
                        result.addAll(tingList);
                    }
                }
            }
        }

        distinct(result);
        return result;
    }

    public static void main(String[] args) {

        Instant time1 = Instant.now();

//        List<Card> cards = Arrays.asList(new Card[]{new WanCard(1)
//                ,new WanCard(1),new WanCard(1),new WanCard(2)});
//        List<Card> cards = Arrays.asList(new Card[]{new WanCard(1)
//                ,new WanCard(1),new WanCard(1),new WanCard(2)
//                ,new WanCard(3),new WanCard(4),new WanCard(5)
//                ,new WanCard(6),new WanCard(7),new WanCard(8)
//                ,new WanCard(9),new WanCard(9),new WanCard(9)});
//        List<Card> cards = Arrays.asList(new Card[]{new WanCard(2)
//                ,new WanCard(2),new WanCard(2),new WanCard(3)
//                ,new WanCard(3),new WanCard(5),new WanCard(6)
//                ,new WanCard(4),new TiaoCard(4),new TiaoCard(4)});
//        List<Card> cards = Arrays.asList(new Card[]{new WanCard(1)
//                ,new WanCard(1),new WanCard(2),new WanCard(2)
//                ,new WanCard(3),new WanCard(3),new WanCard(4)
//                ,new WanCard(6),new WanCard(6),new TiaoCard(8)
//                ,new TiaoCard(8),new TiaoCard(8),new TiaoCard(8)});
        List<Card> cards = Arrays.asList(new Card[]{new WanCard(2)
                ,new WanCard(1),new WanCard(3),new WanCard(3)
                ,new WanCard(3),new WanCard(3),new WanCard(7)
                ,new WanCard(7),new WanCard(8),new WanCard(8)
                ,new WanCard(9),new WanCard(5),new WanCard(6)});
        Set<Card> handCards = new TreeSet<>();
        handCards.addAll(cards);

        HandCard handCard = new HandCard(handCards);
        System.out.println(handCard);
        System.out.println("听： " + checkTingPaiList(handCard.getHandCards()));
//        System.out.println("听7对check： " + checkTingQiDuiCard(new ArrayList<>(handCard.getHandCards())));
//        System.out.println("听： " + check4Card(handCard.getHandCards()));


        Instant time2 = Instant.now();
        System.out.println(time2.toEpochMilli()-time1.toEpochMilli());


    }
}
