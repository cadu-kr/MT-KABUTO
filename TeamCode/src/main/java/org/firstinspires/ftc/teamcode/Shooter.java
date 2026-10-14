package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class Shooter extends LinearOpMode {

    DcMotor shooter;
    Limelight3A limelight;
    double pw = .7;
    boolean ad = false;
    boolean rm = false;

    @Override
    public void runOpMode(){
        shooter = hardwareMap.get(DcMotor.class, "shooter");
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0);

        waitForStart();
        while (opModeIsActive()){
            if (gamepad1.dpad_up && !ad) {
                pw+=.1;
            }
            else if (gamepad1.dpad_down && !rm) {
                pw-=.1;
            }
            ad = gamepad1.dpad_up;
            rm = gamepad1.dpad_down;

            if (gamepad1.right_trigger >= .2) {
                shooter.setPower(pw);
            }

            double voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
            double percentage = (voltage - 10.5) / (13.5 - 10.5) * 100.0;
            percentage = Math.max(0, Math.min(100, percentage));

            telemetry.addData("Bateria do Robô: ", "%.1f%% (%.2fV)", percentage, voltage);
            telemetry.addData("Potencia: ", pw);
            telemetry.update();
        }
    }
}
