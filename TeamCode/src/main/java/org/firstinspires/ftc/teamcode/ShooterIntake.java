package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "TT-Intake")
public class ShooterIntake extends LinearOpMode {

    ColorSensor sensor;
    DcMotor intake;
    Servo compres;

    ElapsedTime espera = new ElapsedTime();
    ElapsedTime maisespera = new ElapsedTime();

    boolean rdespera = false;
    boolean rdmaisespeera = false;
    boolean ap = false;
    int ad = 1;

    @Override
    public void runOpMode(){
        sensor = hardwareMap.get(ColorSensor.class, "cor");
        intake = hardwareMap.get(DcMotor.class, "intake");
        compres = hardwareMap.get(Servo.class, "compressao");

        compres.scaleRange(0, 270);
        compres.setPosition(1);

        waitForStart();
        while (opModeIsActive()){
            int blue = sensor.blue();
            int red = sensor.red();
            double plintake = 0;

            if (red > 100){
                rdespera = true;
                espera.reset();
            }
            if (rdespera){
                if (espera.seconds() < 1){
                    plintake = -1;
                }
                else {
                    rdespera = false;
                }
            }else {
                if (ad % 2 == 0){
                    plintake = 1;
                } else {
                    plintake = 0;
                }
            }
            intake.setPower(plintake);

            if (blue > 100){
                compres.setPosition(0);
                rdmaisespeera = true;
                maisespera.reset();
            }
            if (rdmaisespeera){
                if (maisespera.seconds() > .4){
                    compres.setPosition(1);
                    rdmaisespeera = false;
                }
            }

            if (gamepad1.a && !ap){
                ad = ad +1;
            }
            ap = gamepad1.a;

            double voltage = hardwareMap.voltageSensor.iterator().next().getVoltage();
            double percentage = (voltage - 10.5) / (13.5 - 10.5) * 100.0;
            percentage = Math.max(0, Math.min(100, percentage));

            telemetry.addData("Bateria do Robô: ", "%.1f%% (%.2fV)", percentage, voltage);

            telemetry.addData("Intake: ", plintake);
            telemetry.update();
        }
    }
}
