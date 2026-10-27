package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "TT-Sensor", group = "TESTE")
public class testesensor extends LinearOpMode {

    ColorSensor sensor;
    boolean maiorRed = false;
    boolean maiorBlue = false;

    @Override
    public void runOpMode() throws InterruptedException {
        sensor = hardwareMap.get(ColorSensor.class, "sensor-color");

        waitForStart();
        while (opModeIsActive()) {
            int red = sensor.red();
            int blue = sensor.blue();

            /*if (blue < 250) {
                blue = 0;
            }
            else if (red < 350) {
                red = 0;
            }

            if (blue > red) {
                maiorBlue = true;
            }
            else if (red > blue) {
                maiorRed = true;
            }*/

            telemetry.addData("BLUE: ", blue);
            telemetry.addData("RED: ", red);
            telemetry.update();

        }
    }
}
