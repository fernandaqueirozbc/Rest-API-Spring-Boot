package br.com.bandtec.ac3fernanda.util;

public class PilhaObj<T> {

    private int topo;
    private T[] pilha;
    public PilhaObj(int capacidade) {
        topo = -1;
        pilha = (T[]) new Object[capacidade];
    }
    public boolean isEmpty() {
        return topo == -1;
    }

    public boolean isFull() {
        return topo == pilha.length - 1;
    }

    public void push(T info) {
        if (!isFull()) {
            pilha[++topo] = info;
        }
        else {
            System.out.println("Pilha cheia");
        }
    }
    public T pop() {
        if (!isEmpty()) {
            System.out.println("Retirando o objeto: " + pilha[topo]);
            return pilha[topo--];
        }
        return null;
    }
    public T peek() {
        if(!isEmpty()) {
            return pilha[topo];
        }
        return null;
    }
    public void exibe() {
        if(isEmpty()) {
            System.out.println("Pilha vazia");
        }
        else {
            for(int i = topo; i >= 0; i--) {
                T n = pilha[i];
                System.out.println(n.toString());
            }
        }
    }
    public PilhaObj multipop(int indice){
        PilhaObj pilhaAux = new PilhaObj(pilha.length);
        if (indice <= pilha.length){
            if (!isEmpty()){
                for(int i = 0; i < indice; i++){
                    pilhaAux.push(pop());
                    System.out.println("Retirei o valor: " + pilhaAux.peek() + " da sua pilha principal");
                }
            }else{
                System.out.println("Sua pilha está vazia!, sem necessidade de auxilio.");
            }
        }else{
            return null;
        }
        return pilhaAux;
    }
    public void multipush(PilhaObj<T> paux) {
        if(!paux.isEmpty()){
            for(int i = 0; i < paux.pilha.length; i++){
                if (paux.pilha[i] != null){
                    push(paux.pop());
                    System.out.println("Retirei o valor: " + peek() + " da sua pilha AUXILIAR");
                }
            }
        }else{
            System.out.println("Sua pilha está vazia!, sem necessidade de auxilio.");
        }
    }
}
