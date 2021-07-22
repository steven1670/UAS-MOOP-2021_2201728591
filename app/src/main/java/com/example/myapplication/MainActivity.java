package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


import java.util.Random;

public class MainActivity extends AppCompatActivity {

Armies player1, player2;
Switch s; ImageView winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        s = (Switch) findViewById(R.id.switch1);
        TextView lblBattleCase = (TextView) findViewById(R.id.lblBatlleCase);
        s.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            if (isChecked){
                // Infantry vs Cavalry + Range
                winner = findViewById(R.id.P1winner);
                winner.setVisibility(View.INVISIBLE);
                winner = findViewById(R.id.P2winner);
                winner.setVisibility(View.INVISIBLE);
                lblBattleCase.setText("Infantry vs Calvary & Range");
                resetStat();
                generateP1Army(isChecked);
                generateP2Army(isChecked);
            }else{
                winner = findViewById(R.id.P1winner);
                winner.setVisibility(View.INVISIBLE);
                winner = findViewById(R.id.P2winner);
                winner.setVisibility(View.INVISIBLE);
                resetStat();
                lblBattleCase.setText("Cavalry vs Infantry");
                generateP1Army(isChecked);
                generateP2Army(isChecked);
            }
        });

        gameStart();

        final Button rerollP1, rerollP2;
        rerollP1 = findViewById(R.id.btn_rollP1);
        rerollP2 = findViewById(R.id.btn_rollP2);

        rerollP1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateP1Army(s.isChecked());
            }
        });
        rerollP2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateP2Army(s.isChecked());
            }
        });

    }

    private void gameStart() {
        String[] arrayCB = new String[]{
                "Horse Castle", "Wood Castle", "Steel Castle", "Stone Castle"
        };
        Spinner cb1 = (Spinner) findViewById(R.id.cb1);
        Spinner cb2 = (Spinner) findViewById(R.id.cb2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayCB);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cb1.setAdapter(adapter);
        cb2.setAdapter(adapter);

            //PLAYER 1
            generateP1Army(s.isChecked());

            //PLAYER 2
            generateP2Army(s.isChecked());



        final Button btn = findViewById(R.id.btn_battle);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String P1Castle = cb1.getSelectedItem().toString();
                String P2Castle = cb2.getSelectedItem().toString();
                double cavNum, infanNum, archNum, catNum;

                if (!s.isChecked()){

                    //BATTLE RESOLUTION
                    //PLAYER 1 ATTACKING

                    cavNum = player1.getCalvaryUnits().doubleValue();
                    infanNum = player1.getInfantryUnits().doubleValue();
                    archNum = player1.getArcherUnits().doubleValue();
                    catNum = player1.getCatapultUnits().doubleValue();

                    //castle bonus
                    if (P1Castle.equals("Horse Castle")){
                        cavNum = cavNum + (cavNum * 0.2);
                    }else if(P1Castle.equals("Wood Castle")){
                        archNum = archNum + (archNum * 0.2);
                    }else if(P1Castle.equals("Steel Castle")){
                        infanNum = infanNum + (infanNum * 0.2);
                    }else if(P1Castle.equals("Stone Castle")){
                        catNum = catNum + (catNum * 0.2);
                    }

                    //hero bonus
                    int unitLeftInfan;
                    double bonusToCav;
                    bonusToCav = player1.getHero_cav() * 0.4;
                    unitLeftInfan = (int) Math.floor((player2.getInfantryUnits().doubleValue()) - ( (cavNum* 0.4) + (bonusToCav * cavNum)) );

                    if (( unitLeftInfan) <= 0){
                        //PLAYER 1 WINS
                        TextView lblInfan = (TextView) findViewById(R.id.P2_infanUnits);
                        lblInfan.setText("0");
                        player2.setInfantryUnits(0);
                        winner = findViewById(R.id.P1winner);
                        winner.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "PLAYER 1 WINS. CONGRATS!", Toast.LENGTH_LONG).show();

                        return;
                    }else {
                        player2.setInfantryUnits(unitLeftInfan);
                        TextView lblInfan = (TextView) findViewById(R.id.P2_infanUnits);
                        String temp = String.valueOf(unitLeftInfan);
                        lblInfan.setText(temp);
                    }


                    //PLAYER 2 ATTACKING
                    int unitLeftCav;
                    cavNum = player2.getCalvaryUnits().doubleValue();
                    infanNum = player2.getInfantryUnits().doubleValue();
                    archNum = player2.getArcherUnits().doubleValue();
                    catNum = player2.getCatapultUnits().doubleValue();


                    //castle bonus
                    if (P2Castle.equals("Horse Castle")){
                        cavNum = cavNum + (cavNum * 0.2);
                    }else if(P2Castle.equals("Wood Castle")){
                        archNum = archNum + (archNum * 0.2);
                    }else if(P2Castle.equals("Steel Castle")){
                        infanNum = infanNum + (infanNum * 0.2);
                    }else if(P2Castle.equals("Stone Castle")){
                        catNum = catNum + (catNum * 0.2);
                    }

                    //hero bonus
                    double bonusToInfan;
                    bonusToInfan = player2.getHero_inf() * 0.4;
                    unitLeftCav = (int) Math.floor((player1.getCalvaryUnits().doubleValue()) - ( (infanNum* 0.1) + (bonusToInfan * infanNum) ));

                    if ((unitLeftCav) <= 0){
                        //PLAYER 2 WINS
                        TextView lblCav = (TextView) findViewById(R.id.P1_cavUnits);
                        lblCav.setText("0");
                        player1.setCalvaryUnits(0);
                        winner = findViewById(R.id.P2winner);
                        winner.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "PLAYER 2 WINS. CONGRATS!", Toast.LENGTH_LONG).show();

                        return;
                    }else {
                        player1.setCalvaryUnits(unitLeftCav);
                        TextView lblCav = (TextView) findViewById(R.id.P1_cavUnits);
                        String temp = String.valueOf(unitLeftCav);
                        lblCav.setText(temp);
                    }


                }else if (s.isChecked()){
                    //INfantry vs Cav + archer
                    // P1
                    cavNum = player1.getCalvaryUnits().doubleValue();
                    infanNum = player1.getInfantryUnits().doubleValue();
                    archNum = player1.getArcherUnits().doubleValue();
                    catNum = player1.getCatapultUnits().doubleValue();

                    //castle bonus
                    if (P1Castle.equals("Horse Castle")){
                        cavNum = cavNum + (cavNum * 0.2);
                    }else if(P1Castle.equals("Wood Castle")){
                        archNum = archNum + (archNum * 0.2);
                    }else if(P1Castle.equals("Steel Castle")){
                        infanNum = infanNum + (infanNum * 0.2);
                    }else if(P1Castle.equals("Stone Castle")){
                        catNum = catNum + (catNum * 0.2);
                    }

                    //hero bonus
                    double bonusToInf;
                    bonusToInf = player1.getHero_inf() * 0.4;
                    int unitLeftCav = (int) Math.floor((player2.getCalvaryUnits().doubleValue()) - ( (infanNum* 0.1) + (bonusToInf * infanNum)) );
                    int unitLeftArc = (int) Math.floor((player2.getArcherUnits().doubleValue()) - ( (infanNum* 0.4) + (bonusToInf * infanNum)) );

                    if (unitLeftCav <= 0 && unitLeftArc <= 0){
                        //PLAYER 1 WINS
                        TextView lblcav = (TextView) findViewById(R.id.P2_cavUnits);
                        TextView lblarc = (TextView) findViewById(R.id.P2_archUnits);
                        lblcav.setText("0");
                        lblarc.setText("0");
                        player2.setCalvaryUnits(0);
                        player2.setArcherUnits(0);
                        winner = findViewById(R.id.P1winner);
                        winner.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "PLAYER 1 WINS. CONGRATS!", Toast.LENGTH_LONG).show();

                        return;
                    }else {
                        player2.setInfantryUnits(unitLeftCav);
                        TextView lblcav = (TextView) findViewById(R.id.P2_cavUnits);
                        TextView lblarc = (TextView) findViewById(R.id.P2_archUnits);

                        if (unitLeftCav <= 0){ unitLeftCav = 0; }
                        if (unitLeftArc <= 0){ unitLeftArc = 0; }

                        String temp1 = String.valueOf(unitLeftCav);
                        String temp2 = String.valueOf(unitLeftArc);
                        lblcav.setText(temp1);
                        lblarc.setText(temp2);
                    }


                    //PLAYER 2 ATTACKING | INFANTRY VS CAV + ARCHER

                    cavNum = player2.getCalvaryUnits().doubleValue();
                    infanNum = player2.getInfantryUnits().doubleValue();
                    archNum = player2.getArcherUnits().doubleValue();
                    catNum = player2.getCatapultUnits().doubleValue();


                    //castle bonus
                    if (P2Castle.equals("Horse Castle")){
                        cavNum = cavNum + (cavNum * 0.2);
                    }else if(P2Castle.equals("Wood Castle")){
                        archNum = archNum + (archNum * 0.2);
                    }else if(P2Castle.equals("Steel Castle")){
                        infanNum = infanNum + (infanNum * 0.2);
                    }else if(P2Castle.equals("Stone Castle")){
                        catNum = catNum + (catNum * 0.2);
                    }

                    //hero bonus
                    double bonusToCav, bonusToArc;
                    bonusToCav = player2.getHero_cav() * 0.4;
                    bonusToArc = player2.getHero_arch() * 0.4;
                    int unitLeftInf = (int) Math.floor((player1.getInfantryUnits().doubleValue()) - ( (cavNum* 0.4) + (bonusToCav * cavNum) ));
                    unitLeftInf -= (int) Math.floor( (archNum* 0.4) + (bonusToArc * archNum) );

                    if ((unitLeftInf) <= 0){
                        //PLAYER 2 WINS
                        TextView lblInf = (TextView) findViewById(R.id.P1_infanUnit);
                        lblInf.setText("0");
                        player1.setInfantryUnits(0);
                        winner = findViewById(R.id.P2winner);
                        winner.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "PLAYER 2 WINS. CONGRATS!", Toast.LENGTH_LONG).show();

                        return;
                    }else {
                        player1.setInfantryUnits(unitLeftInf);
                        TextView lblInf = (TextView) findViewById(R.id.P1_infanUnit);
                        String temp = String.valueOf(unitLeftInf);
                        lblInf.setText(temp);
                    }

                }
            }
        });

    }

    private void resetStat() {
        player1 = new Armies( 0, 0 , 0, 0);
        player1.setHeroes(0, 0, 0, 0, 0);
        player2 = new Armies( 0, 0 , 0, 0);
        player2.setHeroes(0, 0, 0, 0, 0);
        TextView p1_cav, p1_inf, p1_arc, p1_cat, p1_hero;
        TextView p2_cav, p2_inf, p2_arc, p2_cat, p2_hero;
        p1_cav = (TextView) findViewById(R.id.P1_cavUnits);
        p1_inf = (TextView) findViewById(R.id.P1_infanUnit);
        p1_arc = (TextView) findViewById(R.id.P1_archUnits);
        p1_cat = (TextView) findViewById(R.id.P1_catUnits);
        p1_hero = (TextView) findViewById(R.id.P1_Heroes);

        p2_cav = (TextView) findViewById(R.id.P2_cavUnits);
        p2_inf = (TextView) findViewById(R.id.P2_infanUnits);
        p2_arc = (TextView) findViewById(R.id.P2_archUnits);
        p2_cat = (TextView) findViewById(R.id.P2_catUnits);
        p2_hero = (TextView) findViewById(R.id.P2_Heroes);

        p1_cav.setText("0");
        p1_inf.setText("0");
        p1_arc.setText("0");
        p1_cat.setText("0");
        p1_hero.setText("0");

        p2_cav.setText("0");
        p2_inf.setText("0");
        p2_arc.setText("0");
        p2_cat.setText("0");
        p2_hero.setText("0");
    }

    private void generateP1Army(Boolean battleCaseswitch) {
        winner = findViewById(R.id.P1winner);
        winner.setVisibility(View.INVISIBLE);
        winner = findViewById(R.id.P2winner);
        winner.setVisibility(View.INVISIBLE);
        if (battleCaseswitch == false){
            // Cavalry vs Infantry
            //PLAYER 1
            int P1hero_cav = 0, P1hero_inf = 0, P1hero_arch = 0, P1hero_cat =0;
            int P1heroes = numRandomizer(5,0);
            for (int i = 0; i < P1heroes; i++){
                int x = numRandomizer(4,1);
                switch (x){
                    case 1:
                        new Hero("Cavalry Hero");
                        P1hero_cav++;
                        break;
                    case 2:
                        new Hero("Infantry Hero");
                        P1hero_inf++;
                        break;
                    case 3:
                        new Hero("Archer Hero");
                        P1hero_arch++;
                        break;
                    case 4:
                        new Hero("Catapult Hero");
                        P1hero_cat++;
                        break;
                }
            }


            Integer P1_calvaryUnits =  numRandomizer(100000, 1) ;
            TextView P1_cavUnits = (TextView) findViewById(R.id.P1_cavUnits);
            P1_cavUnits.setText(P1_calvaryUnits.toString());
            player1 = new Armies( P1_calvaryUnits, 0 , 0, 0);
            player1.setHeroes(P1hero_cav, P1hero_inf, P1hero_arch, P1hero_cat, P1heroes);

            TextView P1Heroes = (TextView) findViewById(R.id.P1_Heroes);
            String temp = P1hero_cav+"Cav, " + P1hero_arch+"Arch, " + P1hero_inf+"Inf, " + P1hero_cat+"Cat";
            P1Heroes.setText(temp);
            // END OF PLAYER 1

        }else if(battleCaseswitch == true){
            // Infantry vs Cavalry + Range
            //PLAYER 1
            int P1hero_cav = 0, P1hero_inf = 0, P1hero_arch = 0, P1hero_cat =0;
            int P1heroes = numRandomizer(5,0);
            for (int i = 0; i < P1heroes; i++){
                int x = numRandomizer(4,1);
                switch (x){
                    case 1:
                        new Hero("Cavalry Hero");
                        P1hero_cav++;
                        break;
                    case 2:
                        new Hero("Infantry Hero");
                        P1hero_inf++;
                        break;
                    case 3:
                        new Hero("Archer Hero");
                        P1hero_arch++;
                        break;
                    case 4:
                        new Hero("Catapult Hero");
                        P1hero_cat++;
                        break;
                }
            }


            Integer P1_infantryUnits =  numRandomizer(100000, 1) ;
            TextView P1_infUnits = (TextView) findViewById(R.id.P1_infanUnit);
            P1_infUnits.setText(P1_infantryUnits.toString());
            player1 = new Armies( 0, P1_infantryUnits , 0, 0);
            player1.setHeroes(P1hero_cav, P1hero_inf, P1hero_arch, P1hero_cat, P1heroes);

            TextView P1Heroes = (TextView) findViewById(R.id.P1_Heroes);
            String temp = P1hero_cav+"Cav, " + P1hero_arch+"Arch, " + P1hero_inf+"Inf, " + P1hero_cat+"Cat";
            P1Heroes.setText(temp);
            // END OF PLAYER 1

        }

    }

    private void generateP2Army(Boolean battleCaseSwitch) {
        winner = findViewById(R.id.P1winner);
        winner.setVisibility(View.INVISIBLE);
        winner = findViewById(R.id.P2winner);
        winner.setVisibility(View.INVISIBLE);
        if (battleCaseSwitch == false){
            // Cavalry vs Infantry
            int P2heroes = numRandomizer(5,0);
            int P2hero_cav = 0, P2hero_inf = 0, P2hero_arch = 0, P2hero_cat =0;
            for (int i = 0; i < P2heroes; i++){
                int x = numRandomizer(4,1);
                switch (x){
                    case 1:
                        new Hero("Cavalry Hero");
                        P2hero_cav++;
                        break;
                    case 2:
                        new Hero("Infantry Hero");
                        P2hero_inf++;
                        break;
                    case 3:
                        new Hero("Archer Hero");
                        P2hero_arch++;
                        break;
                    case 4:
                        new Hero("Catapult Hero");
                        P2hero_cat++;
                        break;
                }
            }
            Integer P2_infantryUnits =  numRandomizer(100000, 1);
            TextView P2_infanUnits = (TextView) findViewById(R.id.P2_infanUnits);
            P2_infanUnits.setText(P2_infantryUnits.toString());
            player2 = new Armies( 0, P2_infantryUnits , 0, 0);
            player2.setHeroes(P2hero_cav, P2hero_inf, P2hero_arch, P2hero_cat, P2heroes);

            TextView P2Heroes = (TextView) findViewById(R.id.P2_Heroes);
            String temp = P2hero_cav+"Cav, " + P2hero_arch+"Arch, " + P2hero_inf+"Inf, " + P2hero_cat+"Cat";
            P2Heroes.setText(temp);
            // END OF PLAYER 2

        }else if(battleCaseSwitch == true){
            // Infantry vs Cavalry + Range
            int P2heroes = numRandomizer(5,0);
            int P2hero_cav = 0, P2hero_inf = 0, P2hero_arch = 0, P2hero_cat =0;
            for (int i = 0; i < P2heroes; i++){
                int x = numRandomizer(4,1);
                switch (x){
                    case 1:
                        new Hero("Cavalry Hero");
                        P2hero_cav++;
                        break;
                    case 2:
                        new Hero("Infantry Hero");
                        P2hero_inf++;
                        break;
                    case 3:
                        new Hero("Archer Hero");
                        P2hero_arch++;
                        break;
                    case 4:
                        new Hero("Catapult Hero");
                        P2hero_cat++;
                        break;
                }
            }
            int temp = 0, maxtemp;
            Integer P2_cavalryUnits = 0, P2_archerUnits =0;
            P2_cavalryUnits =  numRandomizer(100000, 1);
            maxtemp = 100000 -  P2_cavalryUnits;
            temp = P2_cavalryUnits;
            if(temp < 100000){
                P2_archerUnits =  numRandomizer(maxtemp, 1);
                temp += P2_archerUnits ;
                maxtemp -= P2_archerUnits;
            }


            TextView P2_cavUnits = (TextView) findViewById(R.id.P2_cavUnits);
            TextView P2_archUnits = (TextView) findViewById(R.id.P2_archUnits);
            TextView P2_catUnits = (TextView) findViewById(R.id.P2_catUnits);

            P2_cavUnits.setText(P2_cavalryUnits.toString());
            P2_archUnits.setText(P2_archerUnits.toString());

            player2 = new Armies( P2_cavalryUnits, 0 , P2_archerUnits, 0);
            player2.setHeroes(P2hero_cav, P2hero_inf, P2hero_arch, P2hero_cat, P2heroes);

            TextView P2Heroes = (TextView) findViewById(R.id.P2_Heroes);
            String temp2 = P2hero_cav+"Cav, " + P2hero_arch+"Arch, " + P2hero_inf+"Inf, " + P2hero_cat+"Cat";
            P2Heroes.setText(temp2);
            // END OF PLAYER 2
        }
    }

    private int numRandomizer(int max, int min) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


}