package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Rodas2{

    private DcMotor rodaFrenteDireita, rodaFrenteEsquerda, rodaTrasDireita, rodaTrasEsquerda;
    Limelight3A limelight;
    final double crgiro = .03;
    final double giroMinimo = .08;
    public void inicio(HardwareMap hardwareMap) {

        rodaFrenteDireita = hardwareMap.get(DcMotor.class, "F-D");
        rodaFrenteEsquerda = hardwareMap.get(DcMotor.class, "F-E");
        rodaTrasDireita = hardwareMap.get(DcMotor.class, "T-D");
        rodaTrasEsquerda = hardwareMap.get(DcMotor.class, "T-E");

        rodaFrenteDireita.setDirection(DcMotorSimple.Direction.REVERSE);
        rodaTrasDireita.setDirection(DcMotorSimple.Direction.REVERSE);

        rodaFrenteDireita.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaFrenteEsquerda.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaTrasDireita.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaTrasEsquerda.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(60);
        limelight.pipelineSwitch(1);
        limelight.start();
    }
    public void rodar(Gamepad gamepad1){

        double comandoFrente = 0;
        double comandoLateral = 0;
        double comandoGiro = 0;

        if (gamepad1.left_bumper) {
            LLResult resul = limelight.getLatestResult();

            if (resul != null && resul.isValid()) {
                double erroGiro = resul.getTx();

                if (Math.abs(erroGiro) > giroMinimo) {
                    comandoGiro = Math.signum(comandoGiro) * giroMinimo;
                }
            }
            comandoFrente = 0;
            comandoLateral = 0;
        }
        else {
            comandoFrente = -gamepad1.left_stick_y;
            comandoLateral = gamepad1.left_stick_x;
            comandoGiro = gamepad1.right_stick_x;
        }

        double potenciaFrenteEsquerda = comandoFrente + comandoLateral + comandoGiro;
        double potenciaFrenteDireita = comandoFrente - comandoLateral - comandoGiro;
        double potenciaTrasEsquerda = comandoFrente - comandoLateral + comandoGiro;
        double potenciaTrasDireita = comandoFrente + comandoLateral - comandoGiro;

        double max = Math.max(1.0, Math.max(
                Math.max(Math.abs(potenciaFrenteEsquerda), Math.abs(potenciaFrenteDireita)),
                Math.max(Math.abs(potenciaTrasEsquerda), Math.abs(potenciaTrasDireita))));

        potenciaFrenteEsquerda /= max;
        potenciaTrasDireita /= max;
        potenciaTrasEsquerda /= max;
        potenciaTrasDireita /= max;

        rodaFrenteDireita.setPower(potenciaFrenteDireita);
        rodaFrenteEsquerda.setPower(potenciaFrenteEsquerda);
        rodaTrasEsquerda.setPower(potenciaTrasEsquerda);
        rodaTrasDireita.setPower(potenciaTrasDireita);
    }
}