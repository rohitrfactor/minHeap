package com.company;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class Main {
    private static final HashMap<String,Integer> hashtagDic = new HashMap<>();
    public static void main(String[] args) {
  /*      hashtagDic.put("A",5);
        hashtagDic.put("B",4);
        hashtagDic.put("C",3);
        hashtagDic.put("D",2);
        hashtagDic.put("E",25);
        hashtagDic.put("F",72);
        hashtagDic.put("G",62);
        hashtagDic.put("H",25);
        hashtagDic.put("I",24);
        hashtagDic.put("J",24);
        hashtagDic.put("K",26);
        hashtagDic.put("L",24);
        hashtagDic.put("M",42);
        hashtagDic.put("N",42);
        hashtagDic.put("O",52);
        hashtagDic.put("P",86);
        hashtagDic.put("Q",56);
        hashtagDic.put("R",5);
        hashtagDic.put("T",652);
        hashtagDic.put("U",272);
        hashtagDic.put("V",75);
        hashtagDic.put("W",88);
        hashtagDic.put("X",34);
        hashtagDic.put("Y",86);
        hashtagDic.put("Z",4);
        hashtagDic.put("header",2);
        printTrendingHashTags(trendingHashtagsatOnce(hashtagDic,7));*/

        BinaryMinHeap minHeap = new BinaryMinHeap(7,hashtagDic);
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"A"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"A"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"A"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"A"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"A"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"A"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"A"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"A"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"AB"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"AB"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"AB"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"AB"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"AB"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"ABC"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"ABC"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"ABC"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"ABF"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"ABF"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"ABF"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"ABF"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"ABF"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"ABF"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"ABF"));
        printTrendingHashTags(trendingHashtagsOnTheGo(hashtagDic,3,minHeap,"ABC"));

    }

    private static void printTrendingHashTags(String[] hashtags){
        System.out.println("Output : ");
        for(String s : hashtags){
            System.out.println(s+" with value "+hashtagDic.get(s));
        }
    }

    private static void insertHashtag(String x,BinaryMinHeap minHeap){
        if(hashtagDic.get(x) == null){
            hashtagDic.put(x,1);
        }else {
            hashtagDic.put(x, hashtagDic.get(x) + 1);
            minHeap.heapifyDown();
        }
    }

    private static String[] trendingHashtagsOnTheGo(HashMap<String,Integer> hashtagDic,int topHashtagSize,BinaryMinHeap minHeap,String x){
        if(hashtagDic.get(x) == null){
            //Build a heap of topHashtagSize hashtag
            insertHashtag(x,minHeap);
            if(minHeap.heapCurrentSize() <= topHashtagSize){
                minHeap.insertInHeap(x);
            }else{
                if(hashtagDic.get(x) > hashtagDic.get(minHeap.minimumElement())) {
                    minHeap.replaceRoot(x);
                }
            }
        }else{
            insertHashtag(x,minHeap);
        }
        return minHeap.heapSortDecreasingOrder();
    }

    private static String[] trendingHashtagsatOnce(HashMap<String,Integer> hashtagDic,int topHashtagSize) {
        Set<String> keySet = hashtagDic.keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        //Build a heap of topHashtagSize hashtags
        BinaryMinHeap minHeap = new BinaryMinHeap(7,hashtagDic);
        for(int i=0;i<topHashtagSize;i++) {
            minHeap.insertInHeap(keyArray[i]);
        }
        for(int k=topHashtagSize;k<keyArray.length;k++) {
            if(hashtagDic.get(keyArray[k]) > hashtagDic.get(minHeap.minimumElement())) {
                minHeap.replaceRoot(keyArray[k]);
            }
        }
        return minHeap.heapSortDecreasingOrder();
    }
}

class BinaryMinHeap{
    private int maxSize;
    private int currentSize;
    private String[] heapArray;
    private static HashMap<String,Integer> hashtagDic;
    private static final int ROOT = 0;

    public BinaryMinHeap(int capacity,HashMap<String,Integer> hash) {
        this.maxSize = capacity;
        this.currentSize = 0;
        this.heapArray = new String[this.maxSize];
        hashtagDic = hash;
    }

    public int heapCurrentSize(){
        return this.currentSize;
    }

    private int parentIndex(int childIndex) {
        return (childIndex-1)/2;
    }

    private int leftChildIndex(int parentIndex) {
        return parentIndex*2 + 1;
    }
    private int rightChildIndex(int parentIndex) {
        return parentIndex*2+2;
    }

    private boolean hasLeftChild(int index){
        return leftChildIndex(index) < this.currentSize;
    }

    private boolean hasRightChild(int index){
        return rightChildIndex(index) < this.currentSize;
    }

    private boolean hasParent(int childIndex){
        return parentIndex(childIndex) > ROOT;
    }

    private String leftChild(int index){
        return heapArray[leftChildIndex(index)];
    }
    private String rightChild(int index){
        return heapArray[rightChildIndex(index)];
    }
    private String parent(int index){
        return heapArray[parentIndex(index)];
    }
    private boolean isLeafNode(int index) {
        if(index >= (this.currentSize/2) && index <= this.maxSize) {
            return true;
        }
        return false;
    }
    private void swapHeapElements(int firstIndex,int secondIndex) {
        String temp;
        temp = heapArray[firstIndex];
        heapArray[firstIndex] = heapArray[secondIndex];
        heapArray[secondIndex] = temp;
    }


    public String popRoot() {
        if(this.currentSize == 0) throw new IllegalStateException();
        String rootElement = heapArray[ROOT];
        heapArray[ROOT] = heapArray[this.currentSize-1];
        this.currentSize--;
        heapifyDown();
        return rootElement;
    }

    public String minimumElement() {
        if(this.currentSize == 0) throw new IllegalStateException();
        return heapArray[ROOT];
    }

    public boolean insertInHeap(String element) {
        if(this.currentSize<this.maxSize) {
            heapArray[this.currentSize] = element;
            this.currentSize++;
            heapifyUp();
            return true;
        }
        return false;
    }

    public void heapifyUp(){
        int currentElementIndex = this.currentSize-1;
        while(hasParent(currentElementIndex) && hashtagDic.get(heapArray[currentElementIndex]) < hashtagDic.get(heapArray[parentIndex(currentElementIndex)]) ) {
            swapHeapElements(currentElementIndex,parentIndex(currentElementIndex));
            currentElementIndex = parentIndex(currentElementIndex);
        }

    }

    public void heapifyDown(){
        int index = ROOT;
        while(hasLeftChild(index)){
            int smallerChildIndex = leftChildIndex(index);
            if(hasRightChild(index) && hashtagDic.get(rightChild(index)) < hashtagDic.get(leftChild(index)) ){
                smallerChildIndex = rightChildIndex(index);
            }
            if( hashtagDic.get(heapArray[index]) <hashtagDic.get(heapArray[smallerChildIndex]) ){
                break;
            }else{
                swapHeapElements(index,smallerChildIndex);
            }
            index = smallerChildIndex;
        }
    }


    public String[] heapSortDecreasingOrder() {
        for(int i=this.currentSize-1;i>ROOT;i--) {
            swapHeapElements(ROOT,i);
            this.currentSize--;
            heapifyDown();
        }
        return heapArray;
    }

    public boolean replaceRoot(String element) {
        heapArray[ROOT] = element;
        heapifyDown();
        return true;
    }
}
