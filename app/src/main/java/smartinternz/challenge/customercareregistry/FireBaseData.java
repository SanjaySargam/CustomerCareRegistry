package smartinternz.challenge.customercareregistry;

import android.util.ArrayMap;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class FireBaseData {
    public static FirebaseFirestore g_firestore;
    static int temp;
    public static List<MyComplaintsModel> complaintsModelList = new ArrayList<>();
    public static List<PendingModel> pendingModelList = new ArrayList<>();
    public static List<String> complaints = new ArrayList<>();
    public static ProfileModel myProfile = new ProfileModel("NA", null, null, 0,0,0);

    public static void createUserData(String email, String name,String mobileNo, MyCompleteListener completeListener) {
        complaints.clear();
        Map<String, Object> userData = new ArrayMap<>();
        userData.put("EMAIL_ID", email);
        userData.put("NAME", name);
        userData.put("MOBILE_NUMBER", mobileNo);
        userData.put("COMPLAINTS_RAISED", 0);
        userData.put("COMPLAINTS_RESOLVED", 0);
        DocumentReference userDoc = g_firestore.collection("CUSTOMERS").document(FirebaseAuth.getInstance().getUid());
        WriteBatch batch = g_firestore.batch();
        batch.set(userDoc, userData);

        DocumentReference countDoc = g_firestore.collection("CUSTOMERS").document("TOTAL_USERS");
        batch.update(countDoc, "COUNT", FieldValue.increment(1));
        batch.commit()
                .addOnSuccessListener(unused -> completeListener.onSuccess())
                .addOnFailureListener(e -> completeListener.onFailure());


    }
    public static void getUserData(MyCompleteListener completeListener) {
        g_firestore.collection("CUSTOMERS").document(FirebaseAuth.getInstance().getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    myProfile.setName(documentSnapshot.getString("NAME"));
                    myProfile.setEmail(documentSnapshot.getString("EMAIL_ID"));
                    myProfile.setMobNo(documentSnapshot.getString("PHONE"));
                    myProfile.setComplaintsRaised(documentSnapshot.getLong("COMPLAINTS_RAISED").intValue());
                    myProfile.setComplaintsResolved(documentSnapshot.getLong("COMPLAINTS_RESOLVED").intValue());
                    completeListener.onSuccess();
                })
                .addOnFailureListener(e -> completeListener.onFailure());
    }

    public static void addComplaints(String task,String desc,MyCompleteListener completeListener){
        Map<String, Object> userData = new ArrayMap<>();
        userData.put("TITLE", task);
        userData.put("DESCRIPTION",desc );
        userData.put("IS_RESOLVED",false);
        complaints.add("");
        DocumentReference userDoc=g_firestore.collection("CUSTOMERS").document(FirebaseAuth.getInstance().getUid())
                .collection("COMPLAINTS")
                .document(String.valueOf(myProfile.getTotalComp()));
        DocumentReference compDoc=g_firestore.collection("CUSTOMERS").document(FirebaseAuth.getInstance().getUid());

        WriteBatch batch = g_firestore.batch();
        batch.set(userDoc, userData);
        batch.update(compDoc,"COMPLAINTS_RAISED",FieldValue.increment(1));
        batch.commit()
                .addOnSuccessListener(unused ->
                        completeListener.onSuccess())


                .addOnFailureListener(e -> completeListener.onFailure());
    }
    public static void loadComplaints(MyCompleteListener completeListener) {
        complaintsModelList.clear();

        if (myProfile.getTotalComp() == 0) {
            complaints.clear();
        }
        int count = complaints.size();
        if (count != 0) {
            for (int i = 1; i <= count; i++) {
//       temp=0;
                g_firestore.collection("CUSTOMERS").document(FirebaseAuth.getInstance().getUid())
                        .collection("COMPLAINTS")
                        .document(String.valueOf(i))
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            String title = documentSnapshot.getString("TITLE");
                            String desc = documentSnapshot.getString("DESCRIPTION");
                            boolean status= Boolean.TRUE.equals(documentSnapshot.getBoolean("IS_RESOLVED"));

                            complaintsModelList.add(new MyComplaintsModel(
                                    title,
                                    desc,
                                    status
                            ));
                            completeListener.onSuccess();

                        });


            }
        }
    }
    public static void allComplaints(String title,String desc,MyCompleteListener completeListener){
        Map<String, Object> userData = new ArrayMap<>();
        userData.put("TITLE", title);
        userData.put("DESCRIPTION",desc );
        userData.put("USERNAME",myProfile.getName());
        userData.put("EMAIL_ID",myProfile.getEmail());
        userData.put("IS_RESOLVED",false);

        DocumentReference userDoc = g_firestore.collection("ALL_COMPLAINTS").document();
        WriteBatch batch = g_firestore.batch();
        batch.set(userDoc, userData);
        batch.commit()
                .addOnSuccessListener(unused -> {
                        completeListener.onSuccess();
                })
                .addOnFailureListener(e -> {
                    completeListener.onFailure();
                });


    }
    public static void loadPendingComplaints(MyCompleteListener completeListener){
        pendingModelList.clear();
            g_firestore.collection("ALL_COMPLAINTS")
                    .whereEqualTo("IS_RESOLVED",false)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        for (DocumentSnapshot doc : queryDocumentSnapshots) {
                            pendingModelList.add(new PendingModel(
                                    doc.getString("TITLE"),
                                    doc.getString("DESCRIPTION"),
                                    doc.getString("USERNAME"),
                                    doc.getString("EMAIL_ID")


                            ));

                        }
                            completeListener.onSuccess();

                    })
                    .addOnFailureListener(e -> {
                        completeListener.onFailure();
                    });
    }

    public static void loadResolvedComplaints(MyCompleteListener completeListener){
        pendingModelList.clear();
        g_firestore.collection("ALL_COMPLAINTS")
                .whereEqualTo("IS_RESOLVED",true)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        pendingModelList.add(new PendingModel(
                                doc.getString("TITLE"),
                                doc.getString("DESCRIPTION"),
                                doc.getString("USERNAME"),
                                doc.getString("EMAIL_ID")
                        ));

                    }
                    completeListener.onSuccess();

                })
                .addOnFailureListener(e -> {
                    completeListener.onFailure();
                });
    }
    public static void adminLogin_(String email,String password,MyCompleteListener completeListener){

        if (Objects.equals(email, "smartinternz@gmail.com") && Objects.equals(password, "123456")){
            completeListener.onSuccess();
        }
        else {
            completeListener.onFailure();
        }

//        g_firestore.collection("ADMIN_LOGIN")
//                .whereEqualTo("EMAIL_ID",email)
//                .whereEqualTo("PASSWORD",password)
//                .get()
//                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
//                    @Override
//                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
//                        completeListener.onSuccess();
//                    }
//                })
//
//                .addOnFailureListener(e -> {
//                    completeListener.onFailure();
//                });
    }




}
