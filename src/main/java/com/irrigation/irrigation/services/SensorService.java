package com.irrigation.irrigation.services;
import org.springframework.retry.annotation.CircuitBreaker;
import org.springframework.stereotype.Service;

//this Class Using to see system is available or not
@Service
public class SensorService {
    private static final int MAX_RETRY_ATTEMPTS = 3;

    private int retryCount;

    private boolean SensorAvailable;

    public SensorService( ) {
        SensorAvailable = true;
        this.retryCount = 0;
    }
    //@CircuitBreaker(name = "AlertingSystem", fallbackMethod = "SensorNotAvailable")
    // I can use this pattern in this service, but I preferred to make this retry mechanism
    // using threading method and by easy way
    public boolean sendIrrigationRequest() throws InterruptedException {

            if(SensorAvailable){
                SensorAvailable = false;
                return !SensorAvailable;
            }

            if (retryCount < MAX_RETRY_ATTEMPTS) {
                retryCount++;
                Thread.sleep(3000);
                System.out.println("trying to irrigate");
                return sendIrrigationRequest();
            }
            return false;
        }

    public void setSensorAvailable(boolean sensorAvailable) {
        SensorAvailable = sensorAvailable;
    }
}

