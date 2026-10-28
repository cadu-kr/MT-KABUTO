package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "TT-Sensor", group = "TESTE")
public class testesensor extends LinearOpMode {

    ColorSensor sensor;

    static final int limiteAzul = 215;
    static final int limiteVermelho = 280;

    @Override
    public void runOpMode() throws InterruptedException {
        sensor = hardwareMap.get(ColorSensor.class, "sensor-color");

        waitForStart();
        while (opModeIsActive()) {
            int red = sensor.red();
            int blue = sensor.blue();

            if (blue < limiteAzul) {
                blue = 0;
            }
            if (red < limiteVermelho) {
                red = 0;
            }

            boolean maiorBlue = blue > red;
            boolean maiorRed = red > blue;

            if (maiorBlue) {
                telemetry.addData("Detectado", "Nectar");
            } else if (maiorRed) {
                telemetry.addData("Detectado", "Polen");
            } else {
                telemetry.addData("Detectado", "Nada");
            }

            telemetry.addData("BLUE", blue);
            telemetry.addData("RED", red);
            telemetry.update();
        }
    }
}