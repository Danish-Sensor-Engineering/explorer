package dse.demo;

import dse.lib.TelegramError;
import javafx.concurrent.Task;

import java.util.concurrent.Flow;

public class EventProcessTask extends Task<StateModel> implements Flow.Subscriber<Integer>  {

    private Flow.Subscription subscription;
    private final StateModel stateModel;

    private int frequency;
    private long lastTimestamp;


    EventProcessTask(StateModel stateModel) {
        this.stateModel = stateModel;
    }


    @Override
    protected StateModel call() {
        updateValue(stateModel);
        return stateModel;
    }


    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        updateMessage("Starting...");
        subscription.request(1);
    }


    @Override
    public void onNext(Integer measurement) {
        if(measurement < 99) {
            updateMessage(TelegramError.getError(measurement));
            subscription.request(1);
            return;
        }

        stateModel.setMeasurement(measurement);

        frequency++;
        long elapsedNanos = System.nanoTime() - lastTimestamp;
        if(elapsedNanos > 1_000_000_000) {
            lastTimestamp = System.nanoTime();
            stateModel.setFrequency(frequency);
            updateMessage("");
            frequency = 0;
        }

        subscription.request(1);
    }


    @Override
    public void onError(Throwable throwable) {
        System.err.println(throwable.getMessage());
        System.exit(1);
    }


    @Override
    public void onComplete() {
        updateMessage("Stopped.");
    }

}
