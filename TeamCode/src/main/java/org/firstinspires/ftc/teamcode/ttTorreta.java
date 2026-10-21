package org.firstinspires.ftc.teamcode;



import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "TT-Torreta")
public class ttTorreta extends LinearOpMode {

    DcMotor torreta;
    Limelight3A limelight;
    double kp = .02;


    @Override
    public void runOpMode() {

        torreta = hardwareMap.get(DcMotor.class, "torreta");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);
        limelight.start();

        waitForStart();
        while (opModeIsActive()) {
            LLResult result = limelight.getLatestResult();
            if (result != null && result.isValid()) {
                double tx = result.getTx();
                double pw = Range.clip(tx * kp, -.5, .5);

                 if (Math.abs(tx) > 1) {
                   pw = 0;
                 }
                 torreta.setPower(pw);
            }
            else {
                double pwgiro = gamepad1.left_stick_x;
                torreta.setPower(pwgiro);
            }
        }
        limelight.stop();
    }
}
