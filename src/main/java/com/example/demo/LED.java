package com.example.demo;

import java.util.Arrays;

public class LED {

    public String Serial;
    public LedPosition LedPosition;
    public LedStatus Red;
    public LedStatus Green;
    public LedStatus Blue;

    public LED(String serial, LedPosition ledPosition)
    {
        this.Serial = serial;
        this.LedPosition = ledPosition;
        this.Red = LedStatus.Off;
        this.Blue = LedStatus.Off;
        this.Green = LedStatus.Off;
    }

    public LED(String serial, LedPosition ledPosition, LedStatus red, LedStatus green, LedStatus blue)
    {
        this.Red = red;
        this.Green = green;
        this.Blue = blue;
    }

    public LedStatus HasAlreadyColor(Color color)
    {
        int[] bColor = color.ToByte();
        int[] bLedStatus = this.GetLedStatusByteArray();

        Boolean[] p1 = { false, false, false };
        Boolean[] p2 = { false, false, false };

        LedStatus status = LedStatus.Off;

        for(int i = 0; i<bColor.length; i++)
        {
            if(bColor[i] == 1)
            {
                // Prüfe Phase 1
                if(bLedStatus[i] == 2 || bLedStatus[i] == 1)
                {
                    p1[i] = true;
                }

                // Prüfe Phase 2
                if (bLedStatus[i] == 3 || bLedStatus[i] == 1)
                {
                    p2[i] = true;
                }
            }
            else if(bColor[i] == 0)
            {
                // Prüfe Phase 1
                if (bLedStatus[i] == 3 || bLedStatus[i] == 0)
                {
                    p1[i] = true;
                }

                // Prüfe Phase 2
                if (bLedStatus[i] == 2 || bLedStatus[i] == 0)
                {
                    p2[i] = true;
                }
            }
        }

        boolean isAllTrue = Arrays.stream(p1).allMatch(val -> val);

        if(isAllTrue)
        {
            status = LedStatus.Phase1;
        }

        isAllTrue = Arrays.stream(p2).allMatch(val -> val);
        if (isAllTrue)
        {
            status = LedStatus.Phase2;
        }

        isAllTrue = Arrays.stream(p1).allMatch(val -> val) && Arrays.stream(p2).allMatch(val -> val);
        if (isAllTrue)
        {
            status = LedStatus.On;
        }

        return status;
    }

    public Boolean HasFreePhase()
    {
        int[] bLedStatus = this.GetLedStatusByteArray();
        Boolean p1 = true;
        Boolean p2 = true;

        for (int i = 0; i < bLedStatus.length; i++)
        {
            // Prüfe Phase 1
            if (bLedStatus[i] == 2 || bLedStatus[i] == 1)
            {
                p1 = false;
            }

            // Prüfe Phase 2
            if (bLedStatus[i] == 3 || bLedStatus[i] == 1)
            {
                p2 = false;
            }
        }

        return p1 || p2;
    }

    public Phase GetNextFreePhase() throws Exception {
        int[] bLedStatus = this.GetLedStatusByteArray();
        Boolean p1 = false;
        Boolean p2 = false;
        Phase phase = Phase.Phase1;

        for (int i = 0; i < bLedStatus.length; i++)
        {
            // Prüfe Phase 1
            if (bLedStatus[i] == 2 || bLedStatus[i] == 1)
            {
                p1 = true;
            }

            // Prüfe Phase 2
            if (bLedStatus[i] == 3 || bLedStatus[i] == 1)
            {
                p2 = true;
            }
        }

        if(p1 && p2)
        {
            throw new Exception("Beide Phasen sind belegt.");
        }
        else if (!p1 && !p2)
        {
            phase = Phase.Phase1;
        }
        else if(!p1)
        {
            phase = Phase.Phase1;
        }
        else if(!p2)
        {
            phase = Phase.Phase2;
        }


        return phase;
    }

    public Phase GetPhaseWithColor(Color color) throws Exception {
        int[] bColor = color.ToByte();
        int[] bLedStatus = this.GetLedStatusByteArray();

        Boolean[] p1 = { false, false, false };
        Boolean[] p2 = { false, false, false };

        Phase phase = Phase.Phase1;

        for (int i = 0; i < bColor.length; i++)
        {
            if (bColor[i] == 1)
            {
                // Prüfe Phase 1
                if (bLedStatus[i] == 2 || bLedStatus[i] == 1)
                {
                    p1[i] = true;
                }

                // Prüfe Phase 2
                if (bLedStatus[i] == 3 || bLedStatus[i] == 1)
                {
                    p2[i] = true;
                }
            }
            else if (bColor[i] == 0)
            {
                // Prüfe Phase 1
                if (bLedStatus[i] == 3 || bLedStatus[i] == 0)
                {
                    p1[i] = true;
                }

                // Prüfe Phase 2
                if (bLedStatus[i] == 2 || bLedStatus[i] == 0)
                {
                    p2[i] = true;
                }
            }
        }

        boolean isAllTrue = Arrays.stream(p1).allMatch(val -> val);
        if (isAllTrue)
        {
            phase = Phase.Phase1;
        }

        isAllTrue = Arrays.stream(p2).allMatch(val -> val);
        if (isAllTrue)
        {
            phase = Phase.Phase2;
        }

        isAllTrue = Arrays.stream(p1).allMatch(val -> val) && Arrays.stream(p2).allMatch(val -> val);
        if (isAllTrue)
        {
            throw new Exception("Die gesuchte Farbe ist auf dauerleuchten.");
        }

        isAllTrue = Arrays.stream(p1).allMatch(val -> !val) && Arrays.stream(p2).allMatch(val -> !val);
        if(isAllTrue)
        {
            throw new Exception("Die gesuchte Farbe wurde in keiner Phase gefunden.");
        }

        return phase;
    }

    public int[] GetLedStatusByteArray()
    {
        int[] b = { Red.getValue(), Green.getValue(), Blue.getValue() };
        return b;
    }

    public void SetLedStatus(int[] BLedStatus)
    {
        for (int i = 0; i < BLedStatus.length; i++)
        {
            switch(i)
            {
                case 0:
                    switch (BLedStatus[i]) {
                        case 0: Red = LedStatus.Off;
                        break;
                        case 1: Red = LedStatus.On;
                        break;
                        case 2: Red = LedStatus.Phase1;
                            break;
                        case 3: Red = LedStatus.Phase2;
                            break;
                    }
                    break;
                case 1:
                    switch (BLedStatus[i]) {
                        case 0: Green = LedStatus.Off;
                            break;
                        case 1: Green = LedStatus.On;
                            break;
                        case 2: Green = LedStatus.Phase1;
                            break;
                        case 3: Green = LedStatus.Phase2;
                            break;
                    }
                    break;
                case 2:
                    switch (BLedStatus[i]) {
                        case 0: Blue = LedStatus.Off;
                            break;
                        case 1: Blue = LedStatus.On;
                            break;
                        case 2: Blue = LedStatus.Phase1;
                            break;
                        case 3: Blue = LedStatus.Phase2;
                            break;
                    }
                    break;
            }

        }
    }

    public void SetColor(Color color, Phase p)
    {
        int[] bColor = convertColorAndPhaseToLedStatus(color, p);
        int[] bLedStatus = this.GetLedStatusByteArray();

        for (int i = 0; i < bColor.length; i++)
        {
            if(bColor[i] == 0)
            {
                // tu nix
            }
            else if(bLedStatus[i] == 0)
            {
                bLedStatus[i] = bColor[i];
            }
            else if(bLedStatus[i] != 0 && bColor[i] != 0)
            {
                bLedStatus[i] = 1;
            }
        }

        this.SetLedStatus(bLedStatus);
    }

    public void RemoveColor(Color color)
    {
        Phase phase = null;
        try {
            phase = GetPhaseWithColor(color);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int[] bColor = convertColorAndPhaseToLedStatus(color, phase);
        int[] bLedStatus = this.GetLedStatusByteArray();

        for (int i = 0; i < bColor.length; i++)
        {
            if(bColor[i] != 0)
            {
                if(bLedStatus[i] != 0 && bLedStatus[i] != 1)
                {
                    bLedStatus[i] = 0;
                }
                else if(bLedStatus[i] == 1)
                {
                    if(phase == Phase.Phase1)
                    {
                        bLedStatus[i] = Phase.Phase2.getValue();
                    }
                    else if(phase == Phase.Phase2)
                    {
                        bLedStatus[i] = Phase.Phase1.getValue();
                    }
                }
            }
        }

        SetLedStatus(bLedStatus);
    }

    private int[] convertColorAndPhaseToLedStatus(Color color, Phase p)
    {
        int[] bColor = color.ToByte();

        for(int i = 0; i<bColor.length; i++)
        {
            if(bColor[i] == 1)
            {
                bColor[i] = p.getValue();
            }
        }

        return bColor;
    }
}

