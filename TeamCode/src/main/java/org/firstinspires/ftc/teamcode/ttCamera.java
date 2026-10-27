package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.List;

@TeleOp(name = "TT-Camera", group = "TESTE")
public class ttCamera extends LinearOpMode {
    Limelight3A limelight;
    double distanciaTag = -1;

    @Override
    public void runOpMode() throws InterruptedException {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();

        waitForStart();
        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();

            if (result != null && result.isValid()) {
                List<LLResultTypes.FiducialResult> tags = result.getFiducialResults();

                if (!tags.isEmpty()) {
                    Pose3D p = tags.get(0).getTargetPoseCameraSpace();
                    double x = p.getPosition().x;
                    double y = p.getPosition().y;
                    double z = p.getPosition().z;
                    distanciaTag = Math.sqrt(x*x + y*y + z*z);
                }
            }

            telemetry.addData("Distancia: ", distanciaTag);
            telemetry.update();
        }
    }
}
