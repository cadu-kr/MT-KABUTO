package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Rodas {

    private DcMotor rodaFD; // Frente Direita
    private DcMotor rodaFE; // Frente Esquerda
    private DcMotor rodaTD; // Trás Direita
    private DcMotor rodaTE; // Trás Esquerda


    public void inicializar(HardwareMap hardwareMap) {

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


    }


    public void rodar(Gamepad gamepad1) {

        double comandoFrente  = -gamepad1.left_stick_y;
        double comandoLateral =  gamepad1.left_stick_x;
        double comandoGiro    =  gamepad1.right_stick_x;

        double pFE = comandoFrente - comandoLateral + comandoGiro;
        double pFD = -comandoFrente - comandoLateral - comandoGiro;
        double pTE = -comandoFrente - comandoLateral + comandoGiro;
        double pTD = comandoFrente - comandoLateral - comandoGiro;

        rodaFE.setPower(pFE);
        rodaFD.setPower(pFD);
        rodaTE.setPower(pTE);
        rodaTD.setPower(pTD);

    }
}