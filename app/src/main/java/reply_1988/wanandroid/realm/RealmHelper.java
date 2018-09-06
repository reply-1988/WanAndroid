package reply_1988.wanandroid.realm;

import io.realm.RealmConfiguration;

public class RealmHelper {

    private static RealmConfiguration mConfiguration;

    public static RealmConfiguration getConfiguration(String realmName) {

         mConfiguration = new RealmConfiguration.Builder()
                .name(realmName)
                .deleteRealmIfMigrationNeeded()
                .build();
         return mConfiguration;
    }
}
