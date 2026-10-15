package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Rodas2{

    private DcMotor rodaFD, rodaFE, rodaTD, rodaTE;
    Limelight3A limelight;
    final double crgiro = .02;
    public void inicio(HardwareMap hardwareMap) {

        rodaFD = hardwareMap.get(DcMotor.class, "MotorFrenteDireita");
        rodaFE = hardwareMap.get(DcMotor.class, "MotorFrenteEsquerda");
        rodaTD = hardwareMap.get(DcMotor.class, "MotorTrasDireita");
        rodaTE = hardwareMap.get(DcMotor.class, "MotorTrasEsquerda");

        rodaFD.setDirection(DcMotorSimple.Direction.REVERSE);
        rodaTD.setDirection(DcMotorSimple.Direction.REVERSE);

        rodaFD.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaFE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaTD.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rodaTE.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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
                double errgiro = resul.getTx();

                if (Math.abs(errgiro) > 1.0) {
                    comandoGiro = errgiro * crgiro;
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
        double pFE = comandoFrente + comandoLateral + comandoGiro;
        double pFD = comandoFrente - comandoLateral - comandoGiro;
        double pTE = comandoFrente - comandoLateral + comandoGiro;
        double pTD = comandoFrente + comandoLateral - comandoGiro;

        rodaFD.setPower(pFD);
        rodaFE.setPower(pFE);
        rodaTE.setPower(pTE);
        rodaTD.setPower(pTD);
    }
}