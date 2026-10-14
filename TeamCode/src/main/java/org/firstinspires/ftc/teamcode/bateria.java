package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "Bateria-ROBO")
public class bateria extends LinearOpMode {
    @Override
    public void runOpMode(){
        waitForStart();
        while (opModeIsActive()){
            double voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
            double percentage = (voltage - 10.5) / (13.5 - 10.5) * 100.0;
            percentage = Math.max(0, Math.min(100, percentage));

            telemetry.addData("Bateria do Robô: ", "%.1f%% (%.2fV)", percentage, voltage);
        }
    }
}
