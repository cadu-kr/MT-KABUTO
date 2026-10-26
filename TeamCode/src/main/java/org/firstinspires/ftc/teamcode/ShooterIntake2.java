package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ShooterIntake2{

    ColorSensor sensor;
    DcMotor intake;
    Servo compressao;

    ElapsedTime espera = new ElapsedTime();
    ElapsedTime maisEspera = new ElapsedTime();

    boolean rodaEspera = false;
    boolean rodaMaisEspeera = false;
    boolean apertado = false;
    int addIntake = 1;

    public void inicio(HardwareMap hardwareMap) {
        sensor = hardwareMap.get(ColorSensor.class, "cor");
        intake = hardwareMap.get(DcMotor.class, "intake");
        compressao = hardwareMap.get(Servo.class, "compressao");

        compressao.setPosition(1);
    }

    public void rodar(Gamepad gamepad1){

        int blue = sensor.blue();
        int red = sensor.red();
        double potenciaIntake = 0;

        if (blue > 100){
            rodaEspera = true;
            espera.reset();
        }
        if (rodaEspera){
            if (espera.seconds() < .3){
                potenciaIntake = -1;
            }
            else {
                rodaEspera = false;
            }
        }else {
            if (addIntake % 2 == 0){
                potenciaIntake = 1;
            } else {
                potenciaIntake = 0;
            }
        }
        intake.setPower(potenciaIntake);

        if (red > 100){
            compressao.setPosition(0);
            rodaMaisEspeera = true;
            maisEspera.reset();
        }
        if (rodaMaisEspeera){
            if (maisEspera.seconds() > .4){
                compressao.setPosition(1);
                rodaMaisEspeera = false;
            }
        }

        if (gamepad1.a && !apertado){
            addIntake = addIntake +1;
        }
        apertado = gamepad1.a;

    }
    public void telemetry(Telemetry telemetry){
        if (addIntake %2 == 0){
            telemetry.addData("Intake: ", "ON");
        }
        else {
            telemetry.addData("Intake: ", "OFF");
        }
    }
}
