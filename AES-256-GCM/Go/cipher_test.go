package main

import (
	"testing"
)

func TestCipherADecipher(t *testing.T) {
	t.Parallel()
	s := []byte("hello aes-256-gcm !")
	t.Logf("source: %x\n", s)
	t.Logf("source str: %s\n", s)
	
	c, err := AES256GCMChipherData(s)
	if err != nil {
		t.Error(err)
	}
	t.Logf("cipher: %x\n", c)
	t.Logf("cipher str: %s\n", c)
	
	d, err := AES256GCMDeChipherData(c)
	if err != nil {
		t.Error(err)
	}
	t.Logf("decipher: %x\n", d)
	t.Logf("decipher str: %s\n", d)
}
