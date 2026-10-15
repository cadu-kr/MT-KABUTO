package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ShooterIntake{

    ColorSensor sensor;
    DcMotor intake;
    Servo compres;

    ElapsedTime espera = new ElapsedTime();
    ElapsedTime maisespera = new ElapsedTime();

    boolean rdespera = false;
    boolean rdmaisespeera = false;
    boolean ap = false;
    int ad = 1;
    String on = "ON";
    String off = "OFF";

    public void inicio(HardwareMap hardwareMap) {
        sensor = hardwareMap.get(ColorSensor.class, "cor");
        intake = hardwareMap.get(DcMotor.class, "intake");
        compres = hardwareMap.get(Servo.class, "compressao");

        compres.scaleRange(0, 270);
        compres.setPosition(1);
    }

    public void rodar(Gamepad gamepad1){

            int blue = sensor.blue();
            int red = sensor.red();
            double plintake = 0;

            if (red > 100){
                rdespera = true;
                espera.reset();
            }
            if (rdespera){
                if (espera.seconds() < .3){
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

    }
    public void telemetry(Telemetry telemetry){
        if (ad %2 == 0){
            telemetry.addData("Intake: ", on);
        }
        else {
            telemetry.addData("Intake: ", off);
        }
    }
}
