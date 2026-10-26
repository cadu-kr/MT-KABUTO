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
    Servo compressao;

    ElapsedTime espera = new ElapsedTime();
    ElapsedTime maisEspera = new ElapsedTime();

    boolean rodaEspera = false;
    boolean rodaMaisEspera = false;
    boolean ap = false;
    int ad = 1;

    public void inicio(HardwareMap hardwareMap) {
        sensor = hardwareMap.get(ColorSensor.class, "cor");
        intake = hardwareMap.get(DcMotor.class, "intake");
        compressao = hardwareMap.get(Servo.class, "compressao");

        compressao.setPosition(1);
    }

    public void rodar(Gamepad gamepad1){

            int blue = sensor.blue();
            int red = sensor.red();
            double plintake = 0;

            if (red > 100){
                rodaEspera = true;
                espera.reset();
            }
            if (rodaEspera){
                if (espera.seconds() < .3){
                    plintake = -1;
                }
                else {
                    rodaEspera = false;
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
                compressao.setPosition(0);
                rodaMaisEspera = true;
                maisEspera.reset();
            }
            if (rodaMaisEspera){
                if (maisEspera.seconds() > .4){
                    compressao.setPosition(1);
                    rodaMaisEspera = false;
                }
            }

            if (gamepad1.a && !ap){
                ad = ad +1;
            }
            ap = gamepad1.a;

    }
    public void telemetry(Telemetry telemetry){
        if (ad %2 == 0){
            telemetry.addData("Intake: ", "ON");
        }
        else {
            telemetry.addData("Intake: ", "OFF");
        }
    }
}
