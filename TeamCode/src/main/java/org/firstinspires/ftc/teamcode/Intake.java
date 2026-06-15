package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake{

    DcMotor intake1;
    DcMotor intake2;

    public void inicializar(HardwareMap hardwareMap) {

        intake1 = hardwareMap.get(DcMotor.class, "intakeD");
        intake2 = hardwareMap.get(DcMotor.class, "intakeE");

        intake2.setDirection(DcMotor.Direction.REVERSE);

        intake1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void rodar(Gamepad gamepad2) {

        if(gamepad2.right_bumper){
            intake1.setPower(1);
            intake2.setPower(1);
        }
        else{
            intake1.setPower(0);
            intake2.setPower(0);
        }
    }
}