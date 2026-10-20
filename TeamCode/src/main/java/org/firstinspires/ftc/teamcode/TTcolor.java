package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;

@TeleOp(name = "TT-Color")
public class TTcolor extends LinearOpMode {

    ColorSensor sensor;

    @Override
    public void runOpMode() throws InterruptedException {
        sensor = hardwareMap.get(ColorSensor.class, "color");

        waitForStart();
        while (opModeIsActive()) {
            int red = sensor.red();
            int blue = sensor.blue();


            //Ainda preciso testar melhor o sensor para colocar os valores certos no if
            /*if (red < 100) {
                red = 10;
            }
            else if (red > 100) {
                red = 1000;
            }

            if (blue < 100) {
                blue = 10;
            }
            else if (blue > 100) {
                blue = 1000;
            }*/

            telemetry.addData("BLUE: ", blue);
            telemetry.addData("RED: ", red);
        }
    }
}
