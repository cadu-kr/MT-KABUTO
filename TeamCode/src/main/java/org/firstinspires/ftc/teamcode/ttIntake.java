package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "TT-Intake", group = "TESTE")
public class ttIntake extends OpMode {

    DcMotor motorIntake, caminhoShooter;


    public void intake() {
        
    }

    @Override
    public void init() {
        motorIntake = hardwareMap.get(DcMotor.class, "intake");
        motorIntake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        caminhoShooter = hardwareMap.get(DcMotor.class, "caminhoShooter");
        caminhoShooter.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    @Override
    public void loop() {

    }
}
