# House Of Cards [![Build Status](https://travis-ci.org/VelbazhdSoftwareLLC/HouseOfCards.svg?branch=master)](https://travis-ci.org/VelbazhdSoftwareLLC/HouseOfCards)

## Introduction
This a small card game I created for a practical on Object Oriented Programming.

## Game Rules
The game starts with a standard deck of 52 cards. There are also four Houses each “coloured” by one of the four suits. You put one by one the random cards, that the dealer randomly deals from the deck, to one of the different Houses (it is of no importance the cards to be of the same suit with the House), trying to achieve a total of 31 in the house. The total is calculated adding the cards (Ace counts for 11, Kings, Queens, Jacks for 10 and the rest as they are, i.e. the 8 of Spades counts for 8). Every time you manage to achieve a 31 in one of the houses you get a bonus regarding the House in which you achieved that (10 for Spades, 20 for Clubs, 30 for Diamonds, 40 for Hearts) and the House becomes empty (total = 0). If in one of the Houses the total will exceed 31 then this House is closed and you cannot use it any more adding cards to it. In order to win you should add all 52 cards in the Houses and only then your score counts in the hall of fame. You lose if all the four Houses will be closed.

## Six Cards Rule
If the number of cards in a House will be six (6) and the total is less than 31, it counts as a 31, the player gets 50 points and the House becomes empty. 

## Joker Rule
This rule adds 4 Jokers to the deck (each with a different suit). If you add it to the House “coloured” with the same suit (e.g. the Heart special card to the Heart House) then it is like having 31 no matter what was the total there and the player gets 100 points, but if you add it to a House “coloured” with another suit (e.g. the Heart card to the Diamond House) you lose immediately.
