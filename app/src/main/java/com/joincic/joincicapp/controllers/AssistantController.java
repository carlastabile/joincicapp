package com.joincic.joincicapp.controllers;

import android.content.Context;

import com.joincic.joincicapp.models.AssistantModel;
import com.joincic.joincicapp.providers.JoincicProvider;

/**
 * Created by carla on 25/05/15.
 */
public class AssistantController {
    static Context context;

    public static void getInstance(Context c) {
        context = c.getApplicationContext();
    }


    /**
     * Inserts the assistant info into the local database of the device
     *
     * @param assistant the assistant info
     */
    public static void insertAssistant(Assistant assistant) {
        context.getContentResolver().insert(JoincicProvider.CONTENET_URI_ASSISTANTS,
                AssistantModel.assistantToValues(assistant.getParticipante()));
    }
}
