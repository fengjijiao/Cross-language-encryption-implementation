package main

import (
	"crypto/aes"
	"crypto/cipher"
	"encoding/hex"
)

//aes-256-gcm
//reference https://gist.github.com/fengjijiao/4dfe1f68e3dd9d9b7652039fd79751e1
func AES256GCMChipherData(plaintext []byte) ([]byte, error) {
	//cipher key
	newKey, _ := hex.DecodeString("310bc65ed2bfd049a3877465ebd547210a492db4edf03e9506a3f942135d55e4")

	nonce,_ := hex.DecodeString("000000000000000000000000")

	block, err := aes.NewCipher(newKey)
	if err != nil {
		return nil, err
	}
	
	aesgcm, err := cipher.NewGCM(block)
	if err != nil {
		return nil, err
	}
	
	cipherText := aesgcm.Seal(nil, nonce, plaintext, nil)
	return cipherText, nil
}

func AES256GCMDeChipherData(cipherText []byte) ([]byte, error) {
	newKey, _ := hex.DecodeString("310bc65ed2bfd049a3877465ebd547210a492db4edf03e9506a3f942135d55e4")

	nonce,_ := hex.DecodeString("000000000000000000000000")

	block, err := aes.NewCipher(newKey)
	if err != nil {
		return nil, err
	}
	aesgcm, err := cipher.NewGCM(block)
	if err != nil {
		return nil, err
	}
	output, _ := aesgcm.Open(nil, nonce, cipherText, nil)
	return output, err
}
