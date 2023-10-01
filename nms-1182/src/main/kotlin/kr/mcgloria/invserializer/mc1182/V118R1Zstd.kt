package kr.mcgloria.invserializer.mc1182

import com.github.luben.zstd.Zstd
import kr.mcgloria.invserializer.InvSerializer
import net.minecraft.nbt.CompoundTag
import net.minecraft.nbt.ListTag
import net.minecraft.nbt.NbtIo
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import java.io.*
import java.util.*

class V118R1Zstd : InvSerializer {

    override fun itemStackArrayToBase64(items: Array<ItemStack?>): String {
        return try {
            val outputStream = ByteArrayOutputStream()
            val dataOutput = BukkitObjectOutputStream(outputStream)
            dataOutput.writeInt(items.size)
            for (item in items) {
                if (item != null) {
                    dataOutput.writeObject(toBase64(item))
                } else {
                    dataOutput.writeObject(null)
                }
            }
            dataOutput.close()
            Base64.getEncoder().encodeToString(Zstd.compress(outputStream.toByteArray()))
        } catch (e: Exception) {
            throw IllegalStateException("Unable to save item stacks.", e)
        }
    }

    @Throws(IOException::class)
    override fun itemStackArrayFromBase64(data: String?): Array<ItemStack?> {
        return try {
            val compressed = Base64.getDecoder().decode(data)
            val size: Long = Zstd.decompressedSize(compressed)
            val bytes: ByteArray = Zstd.decompress(compressed, size.toInt())
            val inputStream = ByteArrayInputStream(bytes)
            val dataInput = BukkitObjectInputStream(inputStream)
            val items = arrayOfNulls<ItemStack>(dataInput.readInt())
            for (Index in items.indices) {
                val stack = dataInput.readObject() as String
                items[Index] = fromBase64(stack)
            }
            dataInput.close()
            items
        } catch (e: ClassNotFoundException) {
            throw IOException("Unable to decode class type.", e)
        }
    }

    override fun toBase64(item: ItemStack): String {
        val outputStream = ByteArrayOutputStream()
        val dataOutput = DataOutputStream(outputStream)
        val nbtTagListItems = ListTag()
        val nbtTagCompoundItem = CompoundTag()
        val nmsItem: net.minecraft.world.item.ItemStack = CraftItemStack.asNMSCopy(item)
        nmsItem.save(nbtTagCompoundItem)
        nbtTagListItems.add(nbtTagCompoundItem)

        NbtIo.writeCompressed(nbtTagCompoundItem, dataOutput)
        //zstd here
        return Base64.getEncoder().encodeToString(Zstd.compress(outputStream.toByteArray()))
    }

    override fun fromBase64(data: String): ItemStack {
        val compressed = Base64.getDecoder().decode(data)
        val size: Long = Zstd.decompressedSize(compressed)
        val bytes: ByteArray = Zstd.decompress(compressed, size.toInt())
        val inputStream = ByteArrayInputStream(bytes)
        var nbtTagCompoundRoot = NbtIo.readCompressed(inputStream)
        val nmsItem = net.minecraft.world.item.ItemStack.of(nbtTagCompoundRoot)
        return CraftItemStack.asBukkitCopy(nmsItem)
    }

}