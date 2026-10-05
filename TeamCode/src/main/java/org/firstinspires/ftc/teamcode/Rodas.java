package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Rodas extends LinearOpMode {

    private DcMotor rodaFD, rodaFE, rodaTD, rodaTE;
    public void runOpMode(){

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

        waitForStart();
        while (opModeIsActive()) {


            double comandoFrente = -gamepad1.left_stick_y;
            double comandoLateral = gamepad1.left_stick_x;
            double comandoGiro = gamepad1.right_stick_x;

            double pFE = comandoFrente - comandoLateral + comandoGiro;
            double pFD = -comandoFrente - comandoLateral - comandoGiro;
            double pTE = -comandoFrente - comandoLateral + comandoGiro;
            double pTD = comandoFrente - comandoLateral - comandoGiro;

            rodaFD.setPower(pFD);
            rodaFE.setPower(pFE);
            rodaTE.setPower(pTE);
            rodaTD.setPower(pTD);
        }
    }
}