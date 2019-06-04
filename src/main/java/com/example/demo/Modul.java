package com.example.demo;

import java.util.ArrayList;
import java.util.List;

public class Modul {

    public  String Serial;
    private List<LED> _leds = new ArrayList<>();
    public LED LedUp;
    public LED LedDown;


    public Modul(String serial)
    {
        Serial = serial;
        LedUp = new LED(serial, LedPosition.Up);
        LedDown = new LED(serial, LedPosition.Down);
        _leds.add(LedUp);
        _leds.add(LedDown);
    }

    public Modul(String serial, LED up, LED down)
    {
        Serial = serial;
        LedUp = up;
        LedDown = down;
    }

    public void AddFlashLight(Color color) throws Exception {
        LED freeLed;

        // Gucken ob das Modul bereits die Farbe enthält
        if(this.HasAlreadyColor(color))
        {
            throw new Exception("Modul enthält bereits diese Farbe.");
        }

        freeLed = this.GetNextLedWithFreePhase();

        freeLed.SetColor(color, freeLed.GetNextFreePhase());
    }

    public void RemoveFlashLight(Color color) throws Exception {
        LED led;
        if (!this.HasAlreadyColor(color))
        {
            throw new Exception("Modul enthält nicht diese Farbe.");
        }

        led = GetLedWithColor(color);

        led.RemoveColor(color);
    }

    public Boolean HasAlreadyColor(Color color)
    {
        Boolean has = false;

        for(LED moduleLed : _leds)
        {
            if (moduleLed.HasAlreadyColor(color) != LedStatus.Off)
            {
                has = true;
            }
        }

        return has;
    }

    public LED GetLedWithColor(Color color) throws Exception {
        if (!this.HasAlreadyColor(color))
        {
            throw new Exception("Modul enthält nicht diese Farbe.");
        }

        for (LED moduleLed : _leds)
        {
            if (moduleLed.HasAlreadyColor(color) != LedStatus.Off)
            {
                return moduleLed;
            }
        }

        throw new Exception("Es ist ein unbekannter Fehler aufgetreten.");
    }

    public LED GetNextLedWithFreePhase() throws Exception {
        for (LED moduleLed : _leds)
        {
            if (moduleLed.HasFreePhase())
            {
                return moduleLed;
            }
        }

        throw new Exception("Keine freie Phase in den LEDs dieses Moduls vorhanden.");
    }


}
