package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "TT-Banho", group = "TESTE")
public class ttBanho extends OpMode {

    ColorSensor sensor;
    DcMotor caminhoShooter;
    Servo compressao;
    static final int limiteAzul = 215;
    static final int limiteVermelho = 280;
    boolean maiorBlue;
    boolean maiorRed;


    public void detectarColor() {
        int red = sensor.red();
        int blue = sensor.blue();

        if (blue < limiteAzul) {
            blue = 0;
        }
        if (red < limiteVermelho) {
            red = 0;
        }

        maiorBlue = blue > red;
        maiorRed = red > blue;

        if (maiorBlue) {
            telemetry.addData("Detectado", "Nectar");
        }
        else if (maiorRed) {
            telemetry.addData("Detectado", "Polen");
        }
        else {
            telemetry.addData("Detectado", "Nada");
        }
    }

    public void alterarCompressao() {
        if (maiorBlue) {
            compressao.setPosition(0);
            if (compressao.getPosition() == 0) {
                caminhoShooter.setPower(1);
            }
            else {
                caminhoShooter.setPower(0);
            }
        }

        else if (maiorRed) {
            compressao.setPosition(.3);
            if (compressao.getPosition() == .3) {
                caminhoShooter.setPower(1);
            }
            else {
                caminhoShooter.setPower(0);
            }
        }
        else {
            compressao.setPosition(.3);
        }
    }


    @Override
    public void init() {
        sensor = hardwareMap.get(ColorSensor.class, "sensor");

        caminhoShooter = hardwareMap.get(DcMotor.class, "caminhoShooter");
        caminhoShooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        compressao = hardwareMap.get(Servo.class, "compressao");
        compressao.setPosition(.3);
    }

    @Override
    public void loop() {
        detectarColor();
        alterarCompressao();
        telemetry.update();
    }
}
