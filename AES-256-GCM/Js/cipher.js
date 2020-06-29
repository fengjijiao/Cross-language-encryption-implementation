import forge from 'node-forge'

export default {
	encrypt: function(plainText) {
		const key = forge.util.hexToBytes("310bc65ed2bfd049a3877465ebd547210a492db4edf03e9506a3f942135d55e4")
		const iv = forge.util.hexToBytes("000000000000000000000000")
		var cipher = forge.cipher.createCipher('AES-GCM', key);
		cipher.start({
			iv: iv, // should be a 12-byte binary-encoded string or byte buffer
			//additionalData: 'binary-encoded string', // optional
			tagLength: 128 // optional, defaults to 128 bits
		});
		cipher.update(forge.util.createBuffer(plainText));
		cipher.finish();
		return cipher.output.getBytes() + cipher.mode.tag.getBytes();
	},
	decrypt: function(data) {
		const key = forge.util.hexToBytes("310bc65ed2bfd049a3877465ebd547210a492db4edf03e9506a3f942135d55e4")
		const iv = forge.util.hexToBytes("000000000000000000000000")
		var decipher = forge.cipher.createDecipher('AES-GCM', key);
		var cipherText = data.slice(0, data.length - 16);
		var tag = data.slice(data.length - 16, data.length);
		decipher.start({
			iv: iv,
			//additionalData: 'binary-encoded string', // optional
			tagLength: 128, // optional, defaults to 128 bits
			tag: tag // authentication tag from encryption
		});
		decipher.update(cipherText);
		var pass = decipher.finish();
		// pass is false if there was a failure (eg: authentication tag didn't match)
		if(pass) {
			// outputs decrypted bytes
			return decipher.output.getBytes();
		}
		return null;
	}
}
