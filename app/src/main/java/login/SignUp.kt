package login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.widget.Toast
import applicationMain.ui.help.Help
import com.example.semester4.databinding.SignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUp : AppCompatActivity() {

    private lateinit var binding: SignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = SignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.switchSignIn.setOnClickListener {
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
        binding.help2.setOnClickListener {
            val intent = Intent(this, Help::class.java)
            startActivity(intent)}

        binding.signUp.setOnClickListener() {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {

                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val intent = Intent(this, Login::class.java)
                            startActivity(intent)
                        } else {
                            if (it.exception.toString().contains("Weak")) {
                                Toast.makeText(this, "The password was to weak", Toast.LENGTH_LONG)
                                    .show()
                            } else {
                                Toast.makeText(this, it.exception.toString(), Toast.LENGTH_LONG)
                                    .show()
                            }
                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }
}