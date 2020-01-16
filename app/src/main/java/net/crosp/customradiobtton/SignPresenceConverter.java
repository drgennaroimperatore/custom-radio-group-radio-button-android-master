package net.crosp.customradiobtton;

import androidx.room.TypeConverter;

public class SignPresenceConverter {

    @TypeConverter
    public static int fromSignPresence(SignPresence sp)
    {
        int val =-1;
        switch (sp)
        {
            case PRESENT:
                val =0;
                break;

            case NOT_PRESENT:
                val=1;
                break;

            case NOT_OBSERVED:
                val =2;
                break;
        }

        return val;
    }

    @TypeConverter
    public static SignPresence toSignPresence(int val)
    {
        switch (val)
        {
            case 0:
                return SignPresence.PRESENT;

            case 1:
                return SignPresence.NOT_PRESENT;

            case 2:
                return SignPresence.NOT_OBSERVED;

        }
        return SignPresence.NOT_OBSERVED;
    }
}
