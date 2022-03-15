package pss.trabalhofinal.bancodeimagens.model.interfaces;

public interface IObservable {
    public void registerObserver(IObserver observer);

    public void removeObserver(IObserver observer);

    public void notifyObservers(Object obj);
}
