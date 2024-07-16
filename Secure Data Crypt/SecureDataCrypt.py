import os
import hashlib
from cryptography.hazmat.primitives import padding
from cryptography.hazmat.primitives import serialization
from cryptography.hazmat.primitives import hashes
from cryptography.hazmat.primitives.asymmetric import padding as asymmetric_padding
from cryptography.hazmat.primitives.asymmetric import rsa
from cryptography.hazmat.backends import default_backend

class SecureDataCrypt:
    def __init__(self, private_key_path):
        self.private_key_path = private_key_path

    def generate_key(self, length):
        return os.urandom(length)

    def encrypt_data(self, data, symmetric_key):
        cipher = padding.OAEP(
            mgf=padding.MGF1(algorithm=hashes.SHA256()),
            algorithm=hashes.SHA256(),
            label=None
        )
        encryptor = cipher.encryptor()
        encrypted_data = encryptor.update(data) + encryptor.finalize()
        return encrypted_data

    def decrypt_data(self, encrypted_data, symmetric_key):
        cipher = padding.OAEP(
            mgf=padding.MGF1(algorithm=hashes.SHA256()),
            algorithm=hashes.SHA256(),
            label=None
        )
        decryptor = cipher.decryptor()
        decrypted_data = decryptor.update(encrypted_data) + decryptor.finalize()
        return decrypted_data

    def hash_data(self, data):
        hash_obj = hashlib.sha256()
        hash_obj.update(data)
        hashed_data = hash_obj.digest()
        return hashed_data

    def sign_data(self, data):
        with open(self.private_key_path, "rb") as key_file:
            private_key = serialization.load_pem_private_key(
                key_file.read(),
                password=None,
                backend=default_backend()
            )

        signature = private_key.sign(
            data,
            asymmetric_padding.PSS(
                mgf=asymmetric_padding.MGF1(hashes.SHA256()),
                salt_length=asymmetric_padding.PSS.MAX_LENGTH
            ),
            hashes.SHA256()
        )

        return signature

    def verify_signature(self, data, signature, public_key):
        with open(public_key_path, "rb") as key_file:
            public_key = serialization.load_pem_public_key(
                key_file.read(),
                backend=default_backend()
            )

        try:
            public_key.verify(
                signature,
                data,
                asymmetric_padding.PSS(
                    mgf=asymmetric_padding.MGF1(hashes.SHA256()),
                    salt_length=asymmetric_padding.PSS.MAX_LENGTH
                ),
                hashes.SHA256()
            )
            return True
        except:
            return False

if __name__ == "__main__":
    private_key_path = "private_key.pem"
    public_key_path = "public_key.pem"
    data = b"Sensitive information"
    
    sdc = SecureDataCrypt(private_key_path)
    
    symmetric_key = sdc.generate_key(32)
    
    encrypted_data = sdc.encrypt_data(data, symmetric_key)
    
    decrypted_data = sdc.decrypt_data(encrypted_data, symmetric_key)
    
    hashed_data = sdc.hash_data(data)
    
    signature = sdc.sign_data(data)
    
    is_valid_signature = sdc.verify_signature(data, signature, public_key_path)
    print("Signature verification result:", is_valid_signature)
