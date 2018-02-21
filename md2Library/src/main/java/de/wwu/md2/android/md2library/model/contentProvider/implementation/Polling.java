package de.wwu.md2.android.md2library.model.contentProvider.implementation;

/**
 * Created by fernando on 25.07.17.
 */

public class Polling implements Runnable {

    private Md2ContentProviderRegistry cpr;

    public Polling(Md2ContentProviderRegistry cpr) {
        this.cpr = cpr;
    }

    @Override
    public void run() {
        
        while (true){
            for(String key:cpr.getContentProviders().keySet())
                cpr.getContentProviders().get(key).update();

            for(String key:cpr.getMultiContentProvider().keySet())
                cpr.getMultiContentProvider().get(key).update();

            try {
                Thread.sleep (5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }


    }
}
