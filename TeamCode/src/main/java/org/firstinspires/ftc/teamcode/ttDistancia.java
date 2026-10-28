package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

public class ttDistancia extends LinearOpMode {

    Limelight3A limelight;
    double[] distancia = {10, 20, 30, 40, 50}; //---> Definir as distancias para atirar
    double[] potencia = {.55, .6, .7, .8, .9}; //---> Definir as potencias de acordo com as distancias

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();

        waitForStart();
        while (opModeIsActive()) {
            double distanciaTag = -1;
            boolean tagVisivel = false;
            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {
                List<LLResultTypes.FiducialResult> tags = result.getFiducialResults();

                if (!tags.isEmpty()) {
                    Pose3D p = tags.get(0).getTargetPoseCameraSpace();
                    double x = p.getPosition().x;
                    double y = p.getPosition().y;
                    double z = p.getPosition().z;
                    distanciaTag = Math.sqrt(x*x + y*y + z*z);
                    tagVisivel = true;
                }
            }
            if (tagVisivel) {
                int melhorPotencia = 0;
                double menorDiferenca = Double.MAX_VALUE;

                for (int i=0; i < distancia.length; i++) {
                    double diferenca = Math.abs(distancia[i] - distanciaTag);

                    if (diferenca < menorDiferenca) {
                        menorDiferenca = diferenca;
                        melhorPotencia = i;
                    }
                    double potenciaCerta = potencia[melhorPotencia];
                }
                telemetry.addData("Tag Visivel", tagVisivel);
                telemetry.addData("Distancia", distanciaTag);
            }
            else {
                telemetry.addData("Tag Visivel", tagVisivel);
                telemetry.addData("Distancia", "SEM INFORMACAO");
            }
        }
    }
}

